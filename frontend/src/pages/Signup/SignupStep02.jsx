import styles from "@/pages/Signup/Signup.module.scss";
import SignupHeader from "@/components/Header/SignupHeader.jsx";
import Input from "@/components/Input/Input.jsx";
import {useRef, useState} from "react";
import Button from "@/components/Button/Button.jsx";

const SignupStep02 = () => {
    const hasHeader = true;
    const [code, setCode] = useState('');
    const inputRef = useRef(null);
    const handleChange = (e) => {
        const value = e.target.value.replace(/\D/g, "").slice(0, 6);
        setCode(value);
    };
    return(
        <div className={`${styles.signupStep} ${!hasHeader ? styles.noHeader : ""}`}>
            {hasHeader && <SignupHeader/>}
            <div className={styles.signupStep__main}>
                <p className={styles.signupStep__main__title}>문자로 받은<br/>
                    인증번호를 입력해주세요.</p>
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
                        ref={inputRef}
                    />

                    <div className={styles.visualBoxes}>
                        {[...Array(6)].map((_, i) =>(
                            <div key ={i} className={`${styles.digitBox} ${code[i] ? styles.filled : ''}`}>
                                {code[i] || ""}
                            </div>
                        ))}
                    </div>
                </div>
                <div className={styles.btnWrap}>
                    <Button
                        type={"submit"}
                        variant="resend"
                    >
                        메세지 재전송
                    </Button>
                </div>
            </div>
        </div>
    )
}
export default SignupStep02;