import styles from "./Input.module.scss";
import {forwardRef} from "react";
const Input = forwardRef(({
    type ,
    label,
    name,
    value,
    placeholder,
    minLength,
    maxLength,
    onChange,
    required = true,
    className='',
    variant=""},ref) => {
    return (
        <div className={styles.inputWrapper}>
            <label className={"a11y-hidden"} htmlFor={name}>{label}</label>
            <input
                ref={ref}
                type={type}
                name={name}
                value={value}
                placeholder={placeholder}
                minLength={minLength}
                maxLength={maxLength}
                onChange={onChange}
                required={required}
                className={`${styles.input} ${variant ? styles[variant] : ""} ${className}`}
            />
        </div>
    )
});

export default Input