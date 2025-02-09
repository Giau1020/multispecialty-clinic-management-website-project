function forwardTo(url){
  window.location.href = url;
}



async function fetchPost(url, data) {
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
// Check null information login 
function validateNull() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    if (username === "" || password === "") {
        alert("Vui lòng nhập đầy đủ Username và Mật khẩu");
        return false;
    }
    return true;
}
// 

// Save Token and role in localStorage
// localStorage.setItem('username', 'myUsername');


// Lấy username từ localStorage
// const username = localStorage.getItem('username');
// console.log(username); // In ra 'myUsername'


// Check information true or false on db

async function login() {
  // var username = document.getElementById("username").value;
  //   var password = document.getElementById("password").value;


    let username = document.querySelector('#username').value;
    let password = document.querySelector('#password').value;
    let url_get_token = 'http://localhost:8081/api/user/login';
    if (username === "" || password === "") {
      alert("Vui lòng nhập đầy đủ Username và Mật khẩu");
      return false;
    }

    try {
      const data = { username, password };

      // Gửi request đến API và chờ kết quả
      const result = await fetchPost(url_get_token, data);
      
      // Kiểm tra nếu API trả về token và role hợp lệ
      if (result && result.token && result.role) {
          // Lưu token và role vào localStorage
          localStorage.setItem('token', result.token);
          localStorage.setItem('role', result.role);

          console.log('Token:', result.token);
          console.log('Role:', result.role);
          if(result.role == "patient"){
            forwardTo('home.html');
          }
          
      } else {
          console.error('Dữ liệu không hợp lệ:', result);
          alert('Đăng nhập không thành công. Vui lòng thử lại.');
      }
  } catch (error) {
      // Xử lý lỗi nếu có vấn đề khi gọi API
      console.error('Lỗi khi đăng nhập:', error);
      alert("Đã có lỗi xảy ra khi đăng nhập. Vui lòng thử lại sau.");
  }
}