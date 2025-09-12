import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { Box } from '@mui/material';
import { RootState } from './store/store';
import WelcomePage from './components/WelcomePage';
import AuthPage from './components/AuthPage';
import Dashboard from './components/Dashboard';
import CategorySetup from './components/CategorySetup';

function App() {
  const { user, isAuthenticated } = useSelector((state: RootState) => state.auth);
  const hasSetupCategories = user?.preferredCategories && user.preferredCategories.length > 0;

  return (
    <Box sx={{ minHeight: '100vh', backgroundColor: '#f5f5f5' }}>
      <Routes>
        <Route 
          path="/" 
          element={
            !isAuthenticated ? (
              <WelcomePage />
            ) : !hasSetupCategories ? (
              <Navigate to="/setup" replace />
            ) : (
              <Navigate to="/dashboard" replace />
            )
          } 
        />
        
        <Route 
          path="/auth" 
          element={
            !isAuthenticated ? (
              <AuthPage />
            ) : (
              <Navigate to={hasSetupCategories ? "/dashboard" : "/setup"} replace />
            )
          } 
        />
        
        <Route 
          path="/setup" 
          element={
            isAuthenticated ? (
              <CategorySetup />
            ) : (
              <Navigate to="/auth" replace />
            )
          } 
        />
        
        <Route 
          path="/dashboard" 
          element={
            isAuthenticated && hasSetupCategories ? (
              <Dashboard />
            ) : (
              <Navigate to={isAuthenticated ? "/setup" : "/auth"} replace />
            )
          } 
        />
      </Routes>
    </Box>
  );
}

export default App;
