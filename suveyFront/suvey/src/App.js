import logo from './logo.svg';
import './App.css';
import { useEffect } from 'react';
import { call } from './api/ApiService';
import MainPage from './content/MainPage';

function App() {

  useEffect(() => {
    call("/suvey","GET",null);
  }, []);

  return (
    <div className="App">
      <MainPage/>
    </div>
  );
}

export default App;
