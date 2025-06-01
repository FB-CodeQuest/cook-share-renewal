import styles from "./SignupHeader.module.scss";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";

const SignupHeader = () => {
    return (
        <header className={styles.signupHeader}>
            <div className={styles.signupHeader__container}>
                <p className={styles.signupHeader__back}>
                    <FontAwesomeIcon icon={faArrowLeft} size="lg" color="#333" />
                    <span className={'a11y-hidden'}>Back</span>
                </p>
            </div>
        </header>
    )
}
export default SignupHeader;