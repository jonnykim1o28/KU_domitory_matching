import React from 'react';
import {BrowserRouter, Routes, Route}  from 'react-router-dom';
import SignIn from './auth/SignIn';
import SignUp from './auth/SignUp';

import App from './App';
import DesiredPerson from './content/tutorial/DesiredPerson';

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/signin" element={<SignIn/>}/>
        <Route path="/signup" element={<SignUp/>}/>
        <Route path="/desired_person" element={<DesiredPerson/>}/>

      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;