import React, { useState } from 'react';
import {
  Box,
  Container,
  AppBar,
  Toolbar,
  Typography,
  Button,
  Card,
  CardContent,
  Grid,
  Fab,
  Dialog,
  DialogTitle,
  DialogContent,
  TextField,
  DialogActions,
  MenuItem,
  IconButton,
  Avatar,
  Menu
} from '@mui/material';
import {
  Add,
  AccountCircle,
  Logout,
  AttachMoney,
  CreditCard,
  TrendingUp,
  Notifications
} from '@mui/icons-material';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../store/authSlice';
import { RootState, AppDispatch } from '../store/store';

const Dashboard: React.FC = () => {
  const [addTransactionOpen, setAddTransactionOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [transactionForm, setTransactionForm] = useState({
    amount: '',
    description: '',
    merchant: '',
    category: '',
  });

  const dispatch = useDispatch<AppDispatch>();
  const { user } = useSelector((state: RootState) => state.auth);

  const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleProfileMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    dispatch(logout());
    handleProfileMenuClose();
  };

  const handleAddTransaction = () => {
    // TODO: Implement transaction addition
    console.log('Adding transaction:', transactionForm);
    setAddTransactionOpen(false);
    setTransactionForm({
      amount: '',
      description: '',
      merchant: '',
      category: '',
    });
  };

  const categories = [
    'GROCERIES', 'DINING', 'GAS', 'TRAVEL', 'ENTERTAINMENT', 
    'SHOPPING', 'UTILITIES', 'HEALTHCARE', 'EDUCATION', 'TRANSPORTATION', 'OTHER'
  ];

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static" sx={{ backgroundColor: '#1976d2' }}>
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1, fontWeight: 600 }}>
            Personal Finance Dashboard
          </Typography>
          
          <IconButton
            size="large"
            edge="end"
            aria-label="account of current user"
            onClick={handleProfileMenuOpen}
            color="inherit"
          >
            <Avatar sx={{ bgcolor: 'secondary.main' }}>
              {user?.firstName?.[0]}{user?.lastName?.[0]}
            </Avatar>
          </IconButton>
          
          <Menu
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={handleProfileMenuClose}
          >
            <MenuItem onClick={handleProfileMenuClose}>
              <AccountCircle sx={{ mr: 1 }} />
              Profile
            </MenuItem>
            <MenuItem onClick={handleLogout}>
              <Logout sx={{ mr: 1 }} />
              Logout
            </MenuItem>
          </Menu>
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Typography variant="h4" gutterBottom>
          Welcome back, {user?.firstName}!
        </Typography>

        <Grid container spacing={4} sx={{ mb: 4 }}>
          <Grid item xs={12} sm={6} md={3}>
            <Card>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <AttachMoney sx={{ color: 'success.main', mr: 1 }} />
                  <Typography variant="h6">Total Spending</Typography>
                </Box>
                <Typography variant="h4" color="success.main">
                  $1,250.00
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  This month
                </Typography>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} sm={6} md={3}>
            <Card>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <CreditCard sx={{ color: 'primary.main', mr: 1 }} />
                  <Typography variant="h6">Cash Back Earned</Typography>
                </Box>
                <Typography variant="h4" color="primary.main">
                  $24.50
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  This month
                </Typography>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} sm={6} md={3}>
            <Card>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <TrendingUp sx={{ color: 'warning.main', mr: 1 }} />
                  <Typography variant="h6">Transactions</Typography>
                </Box>
                <Typography variant="h4" color="warning.main">
                  42
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  This month
                </Typography>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} sm={6} md={3}>
            <Card>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <Notifications sx={{ color: 'info.main', mr: 1 }} />
                  <Typography variant="h6">Recommendations</Typography>
                </Box>
                <Typography variant="h4" color="info.main">
                  3
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Active
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Card>
          <CardContent>
            <Typography variant="h5" gutterBottom>
              Recent Transactions
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Your recent transactions will appear here. Click the + button to add a new transaction.
            </Typography>
            
            <Box sx={{ mt: 2 }}>
              <Typography variant="body2" color="text.secondary">
                💡 Tip: Add transactions to get personalized credit card recommendations!
              </Typography>
            </Box>
          </CardContent>
        </Card>
      </Container>

      <Fab
        color="primary"
        aria-label="add transaction"
        sx={{ position: 'fixed', bottom: 16, right: 16 }}
        onClick={() => setAddTransactionOpen(true)}
      >
        <Add />
      </Fab>

      {/* Add Transaction Dialog */}
      <Dialog 
        open={addTransactionOpen} 
        onClose={() => setAddTransactionOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>Add New Transaction</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Amount"
            type="number"
            fullWidth
            variant="outlined"
            value={transactionForm.amount}
            onChange={(e) => setTransactionForm({ ...transactionForm, amount: e.target.value })}
            sx={{ mb: 2 }}
          />
          
          <TextField
            margin="dense"
            label="Description"
            fullWidth
            variant="outlined"
            value={transactionForm.description}
            onChange={(e) => setTransactionForm({ ...transactionForm, description: e.target.value })}
            sx={{ mb: 2 }}
          />
          
          <TextField
            margin="dense"
            label="Merchant"
            fullWidth
            variant="outlined"
            value={transactionForm.merchant}
            onChange={(e) => setTransactionForm({ ...transactionForm, merchant: e.target.value })}
            sx={{ mb: 2 }}
          />
          
          <TextField
            select
            margin="dense"
            label="Category"
            fullWidth
            variant="outlined"
            value={transactionForm.category}
            onChange={(e) => setTransactionForm({ ...transactionForm, category: e.target.value })}
          >
            {categories.map((category) => (
              <MenuItem key={category} value={category}>
                {category.charAt(0) + category.slice(1).toLowerCase()}
              </MenuItem>
            ))}
          </TextField>
        </DialogContent>
        
        <DialogActions>
          <Button onClick={() => setAddTransactionOpen(false)}>Cancel</Button>
          <Button onClick={handleAddTransaction} variant="contained">
            Add Transaction
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Dashboard;
