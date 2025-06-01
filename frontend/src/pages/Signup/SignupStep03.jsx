import styles from "@/pages/Signup/Signup.module.scss";
import SignupHeader from "@/components/Header/SignupHeader.jsx";
import Input from "@/components/Input/Input.jsx";
import Button from "@/components/Button/Button.jsx";
import {useState} from "react";

const SignupStep03 = () => {
    const hasHeader = true;
    const [nickname, setNickname] = useState('');
    return (
        <div className={`${styles.signupStep} ${!hasHeader ? styles.noHeader : ""}`}>
            {hasHeader && <SignupHeader/>}
            <div className={styles.signupStep__main}>
                <p className={styles.signupStep__main__title}>반갑습니다!<br/>
                    닉네임을 입력해주세요.</p>
                <div
                    className={styles.nicknameWrap}
                >
                    <Input
                        type={'text'}
                        name={'nickname'}
                        placeholder={'닉네임 입력'}
                        label={'인증번호'}
                        value={nickname}
                        onChange={(e) => setNickname(e.target.value)}
                        maxLength={12}
                        required
                        variant={'signupInput'}
                    />

                </div>
                <div className={`${styles.btnWrap} ${styles.stepBtn}`}>
                    <Button
                        type={"submit"}
                        disabled={!nickname}
                    >
                        계속하기
                    </Button>
                </div>
            </div>
        </div>
    )
}
export default SignupStep03;