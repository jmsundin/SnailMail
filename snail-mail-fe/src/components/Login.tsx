import { useState, useEffect } from 'react';

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        const originalOverflow = document.body.style.overflowY;
        document.body.style.overflowY = 'hidden';
        return () => {
            document.body.style.overflowY = originalOverflow;
        };
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');
        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ username: email, password }),
                credentials: 'include', // Important: send cookies!
            });
            if (response.ok) {
                // Redirect to inbox or home
                window.location.href = '/inbox';
            } else {
                setError('Invalid credentials');
            }
        } catch {
            setError('Login failed');
        }
    };

    return (
        <div className="container d-flex align-items-center justify-content-center" style={{ minHeight: '90vh', overflowY: 'hidden' }}>
            <div className="card p-4 shadow" style={{ minWidth: 350 }}>
                <h2 className="mb-4 text-center">Sign In</h2>
                {error && <div className="alert alert-danger">{error}</div>}
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email address</label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            placeholder="Enter email"
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            placeholder="Password"
                            required
                        />
                    </div>
                    <button type="submit" className="btn btn-primary w-100">Login</button>
                </form>
            </div>
        </div>
    );
}

export { Login }