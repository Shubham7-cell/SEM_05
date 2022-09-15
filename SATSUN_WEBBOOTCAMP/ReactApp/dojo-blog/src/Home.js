import { useState } from "react";

const Home = () => {
    
    const [name, setName] = useState('Shubham');
    const [age, setAge] = useState(19);

    const handleClick = () => {
        setName('Pratik');
        setAge(20);
    }

    // const handleClickAgain = (name, e) => {
    //     console.log('hello ' + name, e.target);
    // }
    
    return ( 
        <div className="home">
            <h2>HOMEPAGE</h2>
            <p>{ name } has born before { age } years</p>
            <button onClick = { handleClick }>Click me!</button>
        </div>
     );
}
 
export default Home;