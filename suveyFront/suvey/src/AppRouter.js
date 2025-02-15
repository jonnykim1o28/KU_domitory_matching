import React from 'react';
import {BrowserRouter, Routes, Route}  from 'react-router-dom';
import SignIn from './auth/SignIn';
import SignUp from './auth/SignUp';

import App from './App';

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/signin" element={<SignIn/>}/>
        <Route path="/signup" element={<SignUp/>}/>

      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;