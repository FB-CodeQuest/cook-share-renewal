import styles from "./SignupHeader.module.scss";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";

const SignupHeader = ({onBack}) => {
    return (
        <header className={styles.signupHeader}>
            <div className={styles.signupHeader__container}>
                <button
                    className={styles.signupHeader__back}
                    type={'button'}
                    onClick={onBack}
                >
                    <FontAwesomeIcon icon={faArrowLeft} size="lg" color="#333" />
                    <span className={'a11y-hidden'}>Back</span>
                </button>
            </div>
        </header>
    )
}
export default SignupHeader;