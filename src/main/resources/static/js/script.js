export function forwardTo(url){
    window.location.href = url;
}
window.forwardTo = forwardTo;


  
export async function fetchPost(url, data) {
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Định dạng dữ liệu là JSON
        },
        body: JSON.stringify(data), // Chuyển đổi dữ liệu thành chuỗi JSON
      });
  
      // Kiểm tra nếu có lỗi với response
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      // Chuyển đổi dữ liệu từ response sang JSON và trả về
      const result = await response.json();
      return result;
    } catch (error) {
      console.error('Error during fetch:', error);
    }
  }
  
  // Hàm gửi yêu cầu API bảo mật với tokens
export async function fetchWithToken(url,token) {
    try {
      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,  // Thêm token vào header Authorization
          'Content-Type': 'application/json'
        }
      });
  
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      const result = await response.json(); // Chuyển dữ liệu từ response thành JSON
      console.log('Protected resource data:', result);
    } catch (error) {
      console.error('Error during fetch:', error);
    }
  }
  