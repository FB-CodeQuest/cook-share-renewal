import SignupStep01 from "@/pages/Signup/SignupStep01.jsx";
import SignupStep02 from "@/pages/Signup/SignupStep02.jsx";
import SignupStep03 from "@/pages/Signup/SignupStep03.jsx";
import SignupStep04 from "@/pages/Signup/SignupStep04.jsx";
import SignupStep05 from "@/pages/Signup/SignupStep05.jsx";
import {useState} from "react";

const Signup = () => {
    const [step, setStep] = useState(1);

    const goNext = () => setStep((prev) => prev + 1)
    const goPrev = () => setStep((prev) => prev - 1)

    const stepProps = {goNext, goPrev}


    return(
        <>
            {step === 1 && <SignupStep01 {...stepProps}/>}
            {step === 2 && <SignupStep02 {...stepProps}/>}
            {step === 3 && <SignupStep03 {...stepProps}/>}
            {step === 4 && <SignupStep04 {...stepProps}/>}
            {step === 5 && <SignupStep05 {...stepProps}/>}
        </>
    )
}
export default Signup;