import styles from "./Signup.module.scss";
import Input from "@/components/Input/Input.jsx";
import {useState} from "react";
import Button from "@/components/Button/Button.jsx";
import {checkPhoneNumber} from "@/api/userApi.js";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faXmark} from "@fortawesome/free-solid-svg-icons/faXmark";
import {smsSend} from "@/api/smsApi.js";

const SignupStep01 = ({ goNext, setPhoneNumber }) => {
    const hasHeader = false;
    const [phone, setPhone] = useState('');
    const [isChecking, setIsChecking] = useState(false);
    const [error, setError] = useState('');

    const handleChange = (e) => {
        const value = e.target.value.replace(/\D/g, "").slice(0, 11);
        setPhone(value);
        setError('');
    };

    const handlePhoneNumber = async() => {
        if (phone.length !== 11) return;

        try{
            setIsChecking(true);

            // 중복확인
            const checkRes = await checkPhoneNumber(phone);
            if(!checkRes.data.success){
                setError(checkRes.data.message);
                return;
            }

            // 인증번호 전송
            const smsRes = await smsSend(phone);
            if(!smsRes.data.success){
                setError(smsRes.data.message);
                return;
            }

            setPhoneNumber(phone);
            goNext();

        }catch(e){
            const msg = e.response?.data?.message || "서버 오류가 발생했습니다.";
            setError(msg);
            console.log(msg);
        }finally {
            setIsChecking(false);
        }
    }
    return(
        <div className={`${styles.signupStep} ${!hasHeader ? styles.noHeader : ""}`}>
            <div className={styles.signupStep__main}>
                {!error ?
                    (
                    <p className={styles.signupStep__main__title}>전화번호를 입력해 주세요.</p>
                    )
                    :
                    (
                        <p className={styles.signupStep__main__errortitle}>{error}</p>
                    )

                }
                <div className={styles.phoneInputWrap}>
                    <span className={styles.phoneInputWrap__country}>+82</span>
                    <Input
                        type="text"
                        placeholder="전화번호 입력"
                        label="전화번호"
                        name="phone"
                        value={phone}
                        required
                        maxLength={11}
                        onChange={handleChange}
                        variant="phone"
                    />
                    {phone &&
                        <Button
                            type="button"
                            onClick={() => {
                                setPhone('')
                                setError('');
                            }}
                            variant="icon"
                            aria-label="전화번호 입력값 지우기"
                        >
                            <FontAwesomeIcon icon={faXmark} color="#fff" />
                        </Button>
                    }
                </div>
            </div>
            <div className={styles.signupStep__bottom}>
                <p className={styles.signupStep__bottom__desc}>
                    인증을 요청하면 14세 이상이며, cook-share의 <span className={styles.underline}>개인정보처리방침</span> 및
                    <span className={styles.underline}> 이용약관</span>에 동의한것으로 간주합니다.
                </p>
                <div className={styles.signupStep__buttonWrap}>
                    <Button
                        type="submit"
                        onClick={handlePhoneNumber}
                        disabled={ phone.length !== 11 || isChecking }
                    >
                        인증번호 받기
                    </Button>
                </div>
            </div>
        </div>
    )
}
export default SignupStep01;