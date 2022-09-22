import { useState } from "react";

const Home = () => {
    
    // const [name, setName] = useState('Shubham');
    // const [age, setAge] = useState(19);

    const [blogs, setBlogs] = useState([
        {title: 'My new website', body:'loreum ipsum...', author: 'mario', id: 1},
        {title: 'Welcome party', body:'loreum ipsum...', author: 'supermario', id: 2},
        {title: 'Pro tips', body:'loreum ipsum...', author: 'metamario', id: 3},
]);

    // const handleClick = () => {
    //     setName('Pratik');
    //     setAge(20);
    // }

    // const handleClickAgain = (name, e) => {
    //     console.log('hello ' + name, e.target);
    // }
    
    return ( 
        <div className="home">
           {blogs.map((blog) => (
            <div className="blog-preview" key = {blog.id}>
                <h2>{ blog.title }</h2>
                <p>Written by { blog.writer }</p>
            </div>
           ))}
        </div>
     );
}
 
export default Home;