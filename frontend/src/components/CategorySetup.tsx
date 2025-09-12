import React, { useState, useEffect } from 'react';
import {
  Box,
  Container,
  Paper,
  Typography,
  Chip,
  Button,
  Card,
  CardContent,
  CardActionArea,
  alpha,
} from '@mui/material';
import { Grid } from '@mui/material';
import {
  ShoppingCart,
  Restaurant,
  LocalGasStation,
  Flight,
  MovieFilter,
  ShoppingBag,
  Home,
  LocalHospital,
  School,
  DirectionsBus,
  Category
} from '@mui/icons-material';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { updateUser } from '../store/authSlice';
import { RootState, AppDispatch } from '../store/store';
import axios from 'axios';

const categories = [
  { id: 'GROCERIES', name: 'Groceries', icon: <ShoppingCart />, color: '#4caf50' },
  { id: 'DINING', name: 'Dining', icon: <Restaurant />, color: '#ff9800' },
  { id: 'GAS', name: 'Gas', icon: <LocalGasStation />, color: '#f44336' },
  { id: 'TRAVEL', name: 'Travel', icon: <Flight />, color: '#2196f3' },
  { id: 'ENTERTAINMENT', name: 'Entertainment', icon: <MovieFilter />, color: '#9c27b0' },
  { id: 'SHOPPING', name: 'Shopping', icon: <ShoppingBag />, color: '#e91e63' },
  { id: 'UTILITIES', name: 'Utilities', icon: <Home />, color: '#607d8b' },
  { id: 'HEALTHCARE', name: 'Healthcare', icon: <LocalHospital />, color: '#00bcd4' },
  { id: 'EDUCATION', name: 'Education', icon: <School />, color: '#3f51b5' },
  { id: 'TRANSPORTATION', name: 'Transportation', icon: <DirectionsBus />, color: '#795548' },
  { id: 'OTHER', name: 'Other', icon: <Category />, color: '#9e9e9e' },
];

const CategorySetup: React.FC = () => {
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { user, token } = useSelector((state: RootState) => state.auth);

  const handleCategoryToggle = (categoryId: string) => {
    setSelectedCategories(prev => 
      prev.includes(categoryId)
        ? prev.filter(id => id !== categoryId)
        : [...prev, categoryId]
    );
  };

  const handleSubmit = async () => {
    if (selectedCategories.length === 0) return;

    setIsLoading(true);
    try {
      const response = await axios.post(
        '/api/users/preferred-categories',
        selectedCategories,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        }
      );

      dispatch(updateUser({
        ...user!,
        preferredCategories: selectedCategories
      }));

      navigate('/dashboard');
    } catch (error) {
      console.error('Error updating categories:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Box 
      sx={{ 
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        py: 4
      }}
    >
      <Container maxWidth="lg">
        <Paper elevation={10} sx={{ p: 4, borderRadius: 2 }}>
          <Box textAlign="center" mb={4}>
            <Typography variant="h4" component="h1" gutterBottom fontWeight={700}>
              Choose Your Spending Categories
            </Typography>
            <Typography variant="body1" color="text.secondary" sx={{ maxWidth: 600, mx: 'auto' }}>
              Select the categories you spend the most on. This will help us provide 
              better credit card recommendations and track your expenses more effectively.
            </Typography>
          </Box>

          <Grid container spacing={3} sx={{ mb: 4 }}>
            {categories.map((category) => (
              <Grid item xs={12} sm={6} md={4} key={category.id}>
                <Card 
                  sx={{ 
                    height: '100%',
                    cursor: 'pointer',
                    transition: 'all 0.3s ease',
                    border: selectedCategories.includes(category.id) 
                      ? `3px solid ${category.color}` 
                      : '3px solid transparent',
                    transform: selectedCategories.includes(category.id) 
                      ? 'scale(1.02)' 
                      : 'scale(1)',
                    backgroundColor: selectedCategories.includes(category.id)
                      ? alpha(category.color, 0.1)
                      : 'white',
                    '&:hover': {
                      transform: 'scale(1.02)',
                      boxShadow: 4
                    }
                  }}
                >
                  <CardActionArea 
                    onClick={() => handleCategoryToggle(category.id)}
                    sx={{ height: '100%' }}
                  >
                    <CardContent sx={{ textAlign: 'center', p: 3 }}>
                      <Box 
                        sx={{ 
                          color: category.color, 
                          mb: 2,
                          '& svg': { fontSize: 40 }
                        }}
                      >
                        {category.icon}
                      </Box>
                      <Typography variant="h6" component="h3" fontWeight={600}>
                        {category.name}
                      </Typography>
                      {selectedCategories.includes(category.id) && (
                        <Chip
                          label="Selected"
                          size="small"
                          sx={{ 
                            mt: 1, 
                            backgroundColor: category.color,
                            color: 'white'
                          }}
                        />
                      )}
                    </CardContent>
                  </CardActionArea>
                </Card>
              </Grid>
            ))}
          </Grid>

          <Box textAlign="center">
            <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
              Selected {selectedCategories.length} categories
            </Typography>
            
            <Button
              variant="contained"
              size="large"
              onClick={handleSubmit}
              disabled={selectedCategories.length === 0 || isLoading}
              sx={{ 
                px: 4, 
                py: 1.5,
                fontSize: '1.1rem',
                fontWeight: 600,
                minWidth: 200
              }}
            >
              {isLoading ? 'Saving...' : 'Continue to Dashboard'}
            </Button>
          </Box>
        </Paper>
      </Container>
    </Box>
  );
};

export default CategorySetup;
