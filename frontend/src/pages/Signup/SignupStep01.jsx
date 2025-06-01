import styles from "./Signup.module.scss";
import Input from "@/components/Input/Input.jsx";
import {useState} from "react";
import Button from "@/components/Button/Button.jsx";

const SignupStep01 = () => {
    const [phone, setPhone] = useState('');

    return(
        <div className={styles.signupStep}>
            <div className={styles.signupStep__main}>
                <p className={styles.signupStep__main__title}>전화번호를 입력해 주세요.</p>
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
                        onChange={(e) => setPhone(e.target.value)}
                        className={`${styles.input} ${styles.input__phone}`}
                    />
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
                        onClick={() => console.log('눌렀음')}
                        disabled={!phone}
                    >
                        인증번호 받기
                    </Button>
                </div>
            </div>
        </div>
    )
}
export default SignupStep01;