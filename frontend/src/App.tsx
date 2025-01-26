import React from 'react';
import PersonList from './containers/PersonList';
import { ToastContainer } from 'react-toastify';

function App() {
  return (
    <div>
      <PersonList />
      <ToastContainer />
    </div>
  );
}

export default App;
