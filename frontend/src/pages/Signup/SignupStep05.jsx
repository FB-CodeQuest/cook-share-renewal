import {useState} from "react";
import styles from "@/pages/Signup/Signup.module.scss";
import SignupHeader from "@/components/Header/SignupHeader.jsx";
import Input from "@/components/Input/Input.jsx";
import Button from "@/components/Button/Button.jsx";

const SignupStep05 = ({goNext,goPrev}) => {
    const hasHeader = true;
    const [passwordConfirm, setPasswordConfirm] = useState('');

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
                        name={'passwordConfirm'}
                        placeholder={'비밀번호 입력'}
                        label={'비밀번호 입력'}
                        value={passwordConfirm}
                        onChange={(e) => setPasswordConfirm(e.target.value)}
                        minLength={8}
                        maxLength={12}
                        required
                        variant={'signupInput'}
                    />

                </div>
                <div className={`${styles.btnWrap} ${styles.stepBtn}`}>
                    <Button
                        type={"submit"}
                        disabled={passwordConfirm.length < 8}
                    >
                        계속하기
                    </Button>
                </div>
            </div>
        </div>
    )
}
export default SignupStep05;