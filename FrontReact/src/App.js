import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Make sure to install axios via npm

function App() {
    const [videos, setVideos] = useState([]);
    const [selectedVideoIndex, setSelectedVideoIndex] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const hardcodedCredentials = {
        username: "admin",
        password: "passw"
    };

    const handleLogin = () => {
        if (username === hardcodedCredentials.username && password === hardcodedCredentials.password) {
            setIsLoggedIn(true);
        } else {
            alert("Invalid credentials");
        }
    };
    useEffect(() => {
        // Replace with your actual API endpoint
        const fetchVideos = async () => {
            try {
                setLoading(true);
                const response = await axios.get('http://localhost:8080/video');
                setVideos(response.data);
                setLoading(false);
            } catch (err) {
                setError(err);
                setLoading(false);
            }
        };

        fetchVideos();
    }, [isLoggedIn]);
    if (!isLoggedIn) {
        return (
            <div>
                <div style={{ display: "flex", flexDirection: "column", alignItems: "start", margin: "0px 3rem" }}>
                    <h1>Integrated Security</h1>
                    <h2>Login</h2>
                </div>
                <input type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Username" />
                <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Password" />
                <button onClick={handleLogin}>Login</button>
            </div>
        );
    }
    return (
        <div className="App" style={{ margin: '0', padding: '0', boxSizing: 'border-box' }}>
            <div style={{ display: "flex", flexDirection: "column", alignItems: "start", margin: "0px 3rem" }}>
                <h1>Integrated Security</h1>

            </div>
            <div style={{ display: "flex", gap: '3rem', margin: "0px 3rem" }}>
                <div style={{ display: "flex", flexDirection: "column", gap: ".25rem", width: '25%', alignItems: "start" }}>
                    {loading && <p>Loading videos...</p>}
                    {error && <p>Error fetching videos: {error.message}</p>}
                    {!loading && !error && videos.map((videoUrl, index) => (
                        <button 
                            key={index} 
                            style={{ width: "100%", display: "flex", justifyContent: "center", borderRadius: '4px', padding: ".5rem", backgroundColor: 'black', color: 'white', fontSize: "1.25rem", cursor: "pointer" }} 
                            onClick={() => setSelectedVideoIndex(index)}>
                            Video {index + 1}
                        </button>
                    ))}
                </div>
                <div style={{}}>
                    {videos.length > 0 && <video src={videos[selectedVideoIndex]} controls style={{ width: '100%' }}></video>}
                </div>
            </div>
        </div>
    );
}

export default App;
