import styles from "./Button.module.scss";
const Button = ({
    type='button',
    onClick,
    children,
    disabled= false,
    className = "",
    variant=""
                }) => {
    return (
        <button
            type={type}
            onClick={onClick}
            disabled={disabled}
            className={`${styles.button} ${variant ? styles[variant] : ""} ${className}`}
        >
            {children}
        </button>
    )
}

export default Button;