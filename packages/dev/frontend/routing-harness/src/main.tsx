import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { HarnessProviders } from './HarnessProviders';
import './global.css';

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <HarnessProviders>
      <App />
    </HarnessProviders>
  </React.StrictMode>
);
