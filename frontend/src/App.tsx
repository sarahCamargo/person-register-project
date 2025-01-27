import React from 'react';
import { ToastContainer } from 'react-toastify';
import PersonPage from './pages/PersonPage';
import { ThemeProvider } from 'styled-components';
import theme from './styles/theme';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <PersonPage />
      <ToastContainer />
    </ThemeProvider>
  );
}

export default App;
