import React from 'react';
import { 
  Box, 
  Container, 
  Typography, 
  Button, 
  Card, 
  CardContent, 
  Chip
} from '@mui/material';
import Grid from '@mui/material/Grid';
import { 
  AccountBalance, 
  CreditCard, 
  TrendingUp, 
  Group 
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';

const WelcomePage: React.FC = () => {
  const navigate = useNavigate();

  const features = [
    {
      icon: <AccountBalance sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Expense Tracking',
      description: 'Track your spending across all categories and get detailed insights'
    },
    {
      icon: <CreditCard sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Credit Card Optimization',
      description: 'Get recommendations for maximum cash back and rewards on every purchase'
    },
    {
      icon: <TrendingUp sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Financial Analytics',
      description: 'Visualize your spending patterns with comprehensive charts and reports'
    },
    {
      icon: <Group sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Multi-User Support',
      description: 'Collaborate with team members on shared financial planning and budgets'
    }
  ];

  return (
    <Box 
      sx={{ 
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        py: 4
      }}
    >
      <Container maxWidth="lg">
        <Box textAlign="center" mb={6}>
          <Typography 
            variant="h2" 
            component="h1" 
            gutterBottom 
            sx={{ 
              color: 'white', 
              fontWeight: 700,
              mb: 2
            }}
          >
            Personal Finance Planner
          </Typography>
          
          <Typography 
            variant="h5" 
            sx={{ 
              color: 'rgba(255, 255, 255, 0.9)', 
              mb: 3,
              maxWidth: 600,
              mx: 'auto'
            }}
          >
            Take control of your finances with intelligent spending insights and 
            credit card optimization recommendations
          </Typography>

          <Box sx={{ mb: 4 }}>
            <Chip 
              label="Multi-User Collaboration" 
              sx={{ 
                mr: 1, 
                mb: 1, 
                backgroundColor: 'rgba(255, 255, 255, 0.2)',
                color: 'white'
              }} 
            />
            <Chip 
              label="Real-time Notifications" 
              sx={{ 
                mr: 1, 
                mb: 1,
                backgroundColor: 'rgba(255, 255, 255, 0.2)',
                color: 'white'
              }} 
            />
            <Chip 
              label="Smart Recommendations" 
              sx={{ 
                mr: 1, 
                mb: 1,
                backgroundColor: 'rgba(255, 255, 255, 0.2)',
                color: 'white'
              }} 
            />
          </Box>

          <Button 
            variant="contained" 
            size="large" 
            onClick={() => navigate('/auth')}
            sx={{ 
              backgroundColor: 'white',
              color: 'primary.main',
              px: 4,
              py: 1.5,
              fontSize: '1.1rem',
              fontWeight: 600,
              '&:hover': {
                backgroundColor: 'rgba(255, 255, 255, 0.9)'
              }
            }}
          >
            Get Started
          </Button>
        </Box>

        <Grid container spacing={4}>
          {features.map((feature, index) => (
            <Grid item xs={12} sm={6} md={3} key={index}>
              <Card 
                sx={{ 
                  height: '100%',
                  backgroundColor: 'rgba(255, 255, 255, 0.95)',
                  backdropFilter: 'blur(10px)',
                  transition: 'transform 0.3s ease-in-out',
                  '&:hover': {
                    transform: 'translateY(-8px)'
                  }
                }}
              >
                <CardContent sx={{ textAlign: 'center', p: 3 }}>
                  <Box mb={2}>
                    {feature.icon}
                  </Box>
                  <Typography variant="h6" component="h3" gutterBottom fontWeight={600}>
                    {feature.title}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {feature.description}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Container>
    </Box>
  );
};

export default WelcomePage;
