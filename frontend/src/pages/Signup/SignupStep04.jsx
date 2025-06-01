import styles from "@/pages/Signup/Signup.module.scss";
import SignupHeader from "@/components/Header/SignupHeader.jsx";
import Input from "@/components/Input/Input.jsx";
import Button from "@/components/Button/Button.jsx";
import {useState} from "react";

const SignupStep04 = ({goNext,goPrev}) => {
    const hasHeader = true;
    const [password, setPassword] = useState('');
    return (
        <div className={`${styles.signupStep} ${!hasHeader ? styles.noHeader : ""}`}>
            {hasHeader && <SignupHeader onBack={goPrev}/>}
            <div className={styles.signupStep__main}>
                <p className={styles.signupStep__main__title}>반갑습니다!<br/>
                    비밀번호를 입력해주세요.</p>
                <div
                    className={styles.inputWrap}
                >
                    <Input
                        type={'password'}
                        name={'password'}
                        placeholder={'비밀번호 입력'}
                        label={'비밀번호'}
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        minLength={8}
                        maxLength={12}
                        required
                        variant={'signupInput'}
                    />

                </div>
                <div className={`${styles.btnWrap} ${styles.stepBtn}`}>
                    <Button
                        type={"submit"}
                        disabled={password.length < 8}
                        onClick={goNext}
                    >
                        계속하기
                    </Button>
                </div>
            </div>
        </div>
    )
}
export default SignupStep04;