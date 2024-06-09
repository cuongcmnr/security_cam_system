import React, { useState, useEffect } from 'react';

function App() {
    const [videos, setVideos] = useState([]);
    const [selectedVideoIndex, setSelectedVideoIndex] = useState(0);
    const [error, setError] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [src,setSrc] = useState("")
    const [loginErr, setloginErr] = useState("")

    useEffect(() => {
        const img = document.getElementById("videoFrame");
        function connect() {
            const ws = new WebSocket("ws://localhost:8080/live/video");
            ws.binaryType = "arraybuffer";
            ws.onmessage = function(event) {
                setError(false)
                const arrayBuffer = event.data;
                console.log(arrayBuffer)
                const blob = new Blob([arrayBuffer], {type: "image/jpeg"});
                setSrc( URL.createObjectURL(blob))
            };

            ws.onclose = function() {
                console.log("WebSocket connection closed, retrying in 1 second...");
                setTimeout(connect, 1000);
            };

            ws.onerror = function() {
                setError(true)
                console.log("WebSocket error");
            };
        }

        connect();
    }, [isLoggedIn]);
    const handleLogin = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const username = formData.get('username');
        const password = formData.get('password');

        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                setError(false)
                setIsLoggedIn(true);
            } else {
                setError(true)
                setloginErr("Invalid Credential, please try again");
            }
        } catch (err) {
            setError('An error occurred. Please try again.');
        }}



    if (!isLoggedIn) {
        return (
            <div>
                <div style={{ display: "flex", flexDirection: "column", alignItems: "start", margin: "0px 3rem" }}>
                    <h1>System Camera</h1>
                    <h2>Login</h2>
                </div>
                {error && <p style={{ color: 'red' }}>{loginErr}</p>}
                <form onSubmit={handleLogin}>
                    <input type="text" placeholder="Username" name="username" />
                    <input type="password" placeholder="Password" name="password" />
                    <button type='submit'>Login</button>
                </form>
            </div>
        );
    }
    return (
        <div className="App" style={{ margin: '0', padding: '0', boxSizing: 'border-box' }}>
            <div style={{ display: "flex", flexDirection: "column", alignItems: "start", margin: "0px 3rem" }}>
                <h1>System Camera</h1>
            </div>
            <div style={{ display: "flex", gap: '3rem', margin: "0px 3rem" }}>
                <div style={{ display: "flex", flexDirection: "column", gap: ".25rem", width: '25%', alignItems: "start" }}>
                    {/* { {loading && <p>Loading videos...</p>} } */}
                    {error && <p>Error fetching videos: {error.message}</p>}
                    {!error &&
                    <div> 
                        <img id="videoFrame" src = {src}/>
                        <button onClick= {async () =>{
                            const response = await fetch('http://localhost:8080/toggleRecording', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                },            
                            });
                        }
                    }>RECORD</button>
                    </div> 
                    }
                </div>
  
            </div>
        </div>
    );
}

export default App;
