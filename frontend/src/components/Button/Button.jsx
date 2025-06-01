import styles from "./Button.module.scss";
const Button = ({
    type='button',
    onClick,
    children,
    disabled= false
                }) => {
    return (
        <button
            type={type}
            onClick={onClick}
            disabled={disabled}
            className={styles.button}
        >
            {children}
        </button>
    )
}

export default Button;