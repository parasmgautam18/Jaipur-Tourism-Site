import React, { useState } from 'react';
import { API_BASE_URL } from '../apiConfig';

function AdminDashboard() {
  const [date, setDate] = useState('');
  const [bookings, setBookings] = useState([]);
  const [hasSearched, setHasSearched] = useState(false);
  const [error, setError] = useState('');

  const fetchBookings = async () => {
    if (!date) return;
    setError('');
    try {
      const response = await fetch(`${API_BASE_URL}/admin/bookings?date=${date}`, {
        credentials: "include"
      });
      if (response.status === 401 || response.status === 403) {
        setError('Forbidden: You are not authorized as an Admin.');
        return;
      }
      if (!response.ok) {
        throw new Error('Failed to fetch');
      }
      const data = await response.json();
      setBookings(data);
      setHasSearched(true);
    } catch (err) {
      setError('Failed to fetch bookings.');
    }
  };

  const toggleContact = async (id) => {
    try {
      const response = await fetch(`${API_BASE_URL}/admin/bookings/${id}/contact`, {
        method: 'PATCH',
        credentials: "include"
      });
      if (response.ok) {
        const data = await response.json();
        setBookings(bookings.map(b => b.id === id ? { ...b, contacted: data.isContacted } : b));
      } else {
        alert('Failed to update contact status');
      }
    } catch (err) {
      console.error('Failed to toggle contact status', err);
    }
  };

  const deleteBooking = async (id) => {
    if (!window.confirm('Are you sure you want to cancel this booking?')) return;
    try {
      const response = await fetch(`${API_BASE_URL}/admin/bookings/${id}`, {
        method: 'DELETE',
        credentials: "include"
      });
      if (response.ok) {
        setBookings(bookings.filter(b => b.id !== id));
      } else {
        alert('Failed to delete booking');
      }
    } catch (err) {
      console.error('Failed to delete booking', err);
    }
  };

  return (
    <main className="admin-container" style={{ maxWidth: '1200px', margin: '40px auto', padding: '20px', minHeight: '60vh', fontFamily: '"Montserrat", sans-serif' }}>
      
      {/* Header Row */}
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '30px', flexWrap: 'wrap', gap: '15px' }}>
        <h1 style={{ margin: 0, color: '#3C2A21', fontSize: '2rem' }}>Admin Dashboard</h1>
        
        <div style={{ display: 'flex', gap: '15px', alignItems: 'center' }}>
          <input 
            type="date" 
            value={date} 
            onChange={(e) => setDate(e.target.value)} 
            style={{ 
              padding: '10px 15px', 
              fontSize: '16px', 
              borderRadius: '8px', 
              border: '1px solid #A1673F',
              backgroundColor: '#FFF6EF',
              color: '#3C2A21',
              outline: 'none',
              fontFamily: '"Montserrat", sans-serif'
            }}
          />
          <button 
            onClick={fetchBookings} 
            style={{ 
              padding: '10px 24px', 
              fontSize: '16px', 
              cursor: 'pointer', 
              backgroundColor: '#A1673F', 
              color: 'white', 
              border: 'none', 
              borderRadius: '8px',
              fontWeight: '600',
              transition: 'background 0.3s',
              fontFamily: '"Montserrat", sans-serif'
            }}
            onMouseOver={(e) => e.target.style.backgroundColor = '#8b5634'}
            onMouseOut={(e) => e.target.style.backgroundColor = '#A1673F'}
          >
            Fetch Bookings
          </button>
        </div>
      </div>

      {error && <p style={{ color: '#D8000C', backgroundColor: '#FFD2D2', padding: '10px', borderRadius: '5px', fontWeight: 'bold' }}>{error}</p>}

      {/* Data Table Card */}
      <div style={{ 
        background: 'white', 
        borderRadius: '12px', 
        boxShadow: '0 4px 15px rgba(0,0,0,0.05)', 
        overflow: 'hidden',
        border: '1px solid #E9CBA7'
      }}>
        <div style={{ overflowX: 'auto' }}>
          <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
            <thead style={{ background: '#A1673F', color: 'white' }}>
              <tr>
                <th style={{ padding: '12px 20px', fontWeight: '600', border: '1px solid #E9CBA7' }}>Name</th>
                <th style={{ padding: '12px 20px', fontWeight: '600', border: '1px solid #E9CBA7' }}>Email</th>
                <th style={{ padding: '12px 20px', fontWeight: '600', border: '1px solid #E9CBA7' }}>Phone</th>
                <th style={{ padding: '12px 20px', fontWeight: '600', border: '1px solid #E9CBA7' }}>Package Name</th>
                <th style={{ padding: '12px 20px', fontWeight: '600', border: '1px solid #E9CBA7' }}>Action</th>
              </tr>
            </thead>
            <tbody>
              {!hasSearched ? (
                <tr>
                  <td colSpan="5" style={{ padding: '40px 20px', textAlign: 'center', color: '#999', fontStyle: 'italic', fontSize: '1.1rem', border: '1px solid #E9CBA7' }}>
                    Select a date to view upcoming visitors
                  </td>
                </tr>
              ) : bookings.length === 0 ? (
                <tr>
                  <td colSpan="5" style={{ padding: '40px 20px', textAlign: 'center', color: '#999', fontStyle: 'italic', fontSize: '1.1rem', border: '1px solid #E9CBA7' }}>
                    No registrations yet.
                  </td>
                </tr>
              ) : (
                bookings.map((booking) => (
                  <tr key={booking.id} style={{ borderBottom: '1px solid #E9CBA7', transition: 'background 0.2s' }} onMouseOver={(e) => e.currentTarget.style.backgroundColor = '#fafafa'} onMouseOut={(e) => e.currentTarget.style.backgroundColor = 'transparent'}>
                    <td style={{ padding: '12px 20px', color: '#333', border: '1px solid #E9CBA7' }}>{booking.userName}</td>
                    <td style={{ padding: '12px 20px', color: '#666', border: '1px solid #E9CBA7' }}>{booking.userEmail}</td>
                    <td style={{ padding: '12px 20px', color: '#666', border: '1px solid #E9CBA7' }}>{booking.userPhone}</td>
                    <td style={{ padding: '12px 20px', color: '#333', fontWeight: '500', border: '1px solid #E9CBA7' }}>{booking.packageName}</td>
                    <td style={{ padding: '12px 20px', border: '1px solid #E9CBA7' }}>
                      <div style={{ display: 'flex', gap: '8px', flexWrap: 'wrap' }}>
                        <button 
                          onClick={() => toggleContact(booking.id)}
                          style={{ 
                            padding: '6px 10px', 
                            background: booking.contacted ? '#e6f4ea' : '#f4f4f4', 
                            border: `1px solid ${booking.contacted ? '#34a853' : '#ddd'}`, 
                            borderRadius: '4px', 
                            cursor: 'pointer',
                            color: booking.contacted ? '#34a853' : '#333',
                            fontSize: '0.85rem',
                            fontWeight: booking.contacted ? '600' : 'normal',
                            display: 'flex',
                            alignItems: 'center',
                            gap: '4px'
                          }}>
                          {booking.contacted ? '✅ Contacted' : 'Mark as Contacted'}
                        </button>
                        
                        <a 
                          href={`https://mail.google.com/mail/?view=cm&fs=1&to=${booking.userEmail}&su=${encodeURIComponent(`Jaipur Tour Reminder: ${booking.packageName}`)}&body=${encodeURIComponent(`Namaste ${booking.userName}, we are excited to see you tomorrow for your tour!`)}`}
                          target="_blank"
                          rel="noopener noreferrer"
                          style={{ 
                            padding: '6px 10px', 
                            background: '#e8f0fe', 
                            border: '1px solid #4285f4', 
                            borderRadius: '4px', 
                            cursor: 'pointer',
                            color: '#1a73e8',
                            fontSize: '0.85rem',
                            textDecoration: 'none',
                            display: 'flex',
                            alignItems: 'center',
                            gap: '4px'
                          }}>
                          ✉️ Send Reminder
                        </a>
                        
                        <button 
                          onClick={() => deleteBooking(booking.id)}
                          style={{ 
                            padding: '6px 10px', 
                            background: '#fce8e6', 
                            border: '1px solid #ea4335', 
                            borderRadius: '4px', 
                            cursor: 'pointer',
                            color: '#c5221f',
                            fontSize: '0.85rem',
                            display: 'flex',
                            alignItems: 'center',
                            gap: '4px'
                          }}>
                          🗑️ Cancel
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </main>
  );
}

export default AdminDashboard;
