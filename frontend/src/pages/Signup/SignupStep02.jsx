import styles from "@/pages/Signup/Signup.module.scss";
import SignupHeader from "@/components/Header/SignupHeader.jsx";
import Input from "@/components/Input/Input.jsx";
import {useEffect, useRef, useState} from "react";
import Button from "@/components/Button/Button.jsx";
import {smsSend, smsVerify} from "@/api/smsApi.js";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faClock} from "@fortawesome/free-solid-svg-icons";

const SignupStep02 = ({goNext,goPrev, phoneNumber}) => {
    const hasHeader = true;
    const [code, setCode] = useState('');
    const [error, setError] = useState('');
    const [timer, setTimer] = useState(180);
    const [reSend, setReSend] = useState(false);
    const inputRef = useRef(null);
    const handleChange = (e) => {
        const value = e.target.value.replace(/\D/g, "").slice(0, 6);
        setCode(value);
        setError('');
    };

    const handleReSend = async() => {
        try{
            // 인증번호 재전송
            const smsRes = await smsSend(phoneNumber);
            if(smsRes.data.success){
                setTimer(180);
                setReSend(false);
            }else{
                setError(smsRes.data.message);
            }
        }catch(e){
            const msg = e.response?.data?.message || "서버 오류가 발생했습니다.";
            setError(msg);
            console.log(msg);
        }
    }

    useEffect(()=>{
        const countDown = setInterval(() => {
            setTimer((prev) => {
                if(prev <= 1){
                    setReSend(true);
                    return 0;
                }
                return prev - 1;
            });
        },1000);

        return () => clearInterval(countDown);
    },[reSend]);

    useEffect(() => {
        if(code.length !== 6) return;
        const verifyCode = async () => {
            try{
                const res = await smsVerify(phoneNumber, code);
                const status = res.status;

                if(status === 200){
                    // 인증 성공
                    goNext();
                } else if (status === 400){
                    // 잘못된 인증번호
                    setError(res.data.message ||"인증번호를 다시 입력해주세요! ");
                    console.error("잘못된 인증번호:", res.status);
                } else if (status === 429){
                    // 재요청 제한
                    setError(res.data.message);
                    console.error("요청 제한:", res.status);
                }else{
                    setError("알수 없는 오류가 발생했습니다");
                    console.error("예외 상태", res.status);
                }
            }catch(e){
                const msg = e.response?.data?.message || "서버 오류가 발생했습니다.";
                setError(msg);
                console.log(msg);
                console.error("서버오류", e.response.status);
            }
        };

        verifyCode();

    }, [code, phoneNumber, goNext]);
    return(
        <div className={`${styles.signupStep} ${!hasHeader ? styles.noHeader : ""}`}>
            {hasHeader && <SignupHeader onBack={goPrev}/>}
            <div className={styles.signupStep__main}>
                {!error ?
                    (
                        <p className={styles.signupStep__main__title}>문자로 받은<br/>
                            인증번호를 입력해주세요.</p>
                    )
                    :
                    (
                        <p className={styles.signupStep__main__errortitle}>{error}</p>
                    )
                }

                <div
                    className={styles.codeInputWrap}
                    onClick={() => inputRef.current?.focus()}
                >
                    <Input
                        type={'text'}
                        name={'authCode'}
                        label={'인증번호'}
                        value={code}
                        onChange={handleChange}
                        maxLength={6}
                        required
                        className={'a11y-hidden'}
                        variant={'authCode'}
                        ref={inputRef}
                    />

                    <div className={styles.visualBoxes}>
                        {[...Array(6)].map((_, i) =>(
                            <div key ={i} className={`${styles.digitBox} ${!error && code[i] ? styles.filled : ''}`}>
                                {code[i] || ""}
                            </div>
                        ))}
                    </div>
                </div>
                <div className={styles.btnWrap}>
                    {reSend ?
                        (
                            <Button
                                type={"submit"}
                                variant="resend"
                                onClick={handleReSend}
                                // disabled={isSending}
                            >
                                메세지 재전송
                            </Button>
                        )
                        :
                        (
                            <p className={styles.timerText}>
                                <FontAwesomeIcon icon={faClock} size="lg" color='#FF7A00'/>
                                남은 시간 {Math.floor(timer / 60)}:{(timer % 60).toString().padStart(2, "0")}
                            </p>
                        )
                    }
                </div>
            </div>
        </div>
    )
}
export default SignupStep02;