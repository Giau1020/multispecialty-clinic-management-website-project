

function toggleFAQ(element) {
    var answer = element.nextElementSibling;
    if (answer.style.display === "none" || answer.style.display === "") {
        answer.style.display = "block";
    } else {
        answer.style.display = "none";
    }
}
// Hàm FETCH GET
async function fetchGet(url) {
  try {
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json', // Định dạng dữ liệu là JSON
      },
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

async function fetchWithToken(url,token) {
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

      return result;
    } catch (error) {
      console.error('Error during fetch:', error);
    }
  }
const token = localStorage.getItem('token');
const role = localStorage.getItem('role');

async function load() {
    // console.log("tokenaaaaaaaaaaa");
    const token = localStorage.getItem("token");
    console.log("token" ,token);
    let url = "http://localhost:8081/api/user/profile";

    if (!token) {
        window.location.href = "/html/login.html";
        return;
    }else {
      console.log("token", token);
      let result = await fetchWithToken(url, token);


      

      if (!result.username) {
          console.error("Lỗi: Không tìm thấy 'username' trong dữ liệu API");
          return;
      }
      document.querySelector('.btn-login').style.display = 'none';

      const parentDiv = document.querySelector(".nav-right");

      const newDiv = document.createElement("div");
      newDiv.classList.add("childDiv");
      // Tạo phần tử hình ảnh
      const newImage = document.createElement("img");
      newImage.src = "../images/avatar.png"; // Thay bằng URL hình ảnh bạn muốn
      newImage.alt = "avatar";

      // Tạo phần tử p
      const newParagraph = document.createElement("p");
      newParagraph.textContent = result.fullName;

      // Thêm hình ảnh và đoạn văn vào div con
      newDiv.appendChild(newImage);
      newDiv.appendChild(newParagraph);

      // Thêm div con vào phần tử cha
      parentDiv.appendChild(newDiv);

    }
}
load(); 



// Show all department on homepage
async function showAllDepartment(){
  let url = "http://localhost:8081/api/department";
  try{
    console.log("ZOOOO");

    let response = await fetchGet(url);
    if(response){
      //get div parentsspecialties-content
      const parentDiv = document.querySelector(".specialties");

      
        response.forEach(item => {

          //Tạo div con
          let newDiv = document.createElement("div");
          newDiv.classList.add("specialty");

          // Tạo phần tử hình ảnh
          const newImage = document.createElement("img");
          newImage.src = item.url; // Thay bằng URL hình ảnh bạn muốn
          newImage.alt = "avatar";

          const newParagraph = document.createElement("h3");
          newParagraph.textContent = item.name;

          // Thêm hình ảnh và đoạn văn vào div con
          newDiv.appendChild(newImage);
          newDiv.appendChild(newParagraph);

          // Thêm div con vào phần tử cha
          parentDiv.appendChild(newDiv);

          

        });
      
    }
  }catch  (error) {
    // Xử lý lỗi nếu có vấn đề khi gọi API
    console.error('Lỗi khi lấy dữ liệu:', error);
    alert("Đã có lỗi xảy ra khi lấy dữ liệu. Vui lòng thử lại sau.");
  }
}
showAllDepartment();
async function showDropdownSpecialty() {
  let url = "http://localhost:8081/api/department";
  try {
      let result = await fetchGet(url);
      if (result && result.length > 0) {
          console.log("Dữ liệu chuyên khoa nhận được:", result);

          // Lấy div cha
          const parentDiv = document.querySelector('.dropdown-specialty');

          // Xóa danh sách cũ nếu có
          parentDiv.innerHTML = "";

          // Tạo danh sách ul
          const ul = document.createElement('ul');

          // Duyệt qua result để tạo danh sách
          result.forEach(item => {
              let li = document.createElement('li');
              li.innerText = item.name;
              ul.appendChild(li);
          });

          // Thêm ul vào div
          parentDiv.appendChild(ul);
          parentDiv.style.display = 'grid';
      } else {
          console.warn("Không có dữ liệu chuyên khoa từ API.");
      }
  } catch (error) {
      console.error("Lỗi khi lấy dữ liệu chuyên khoa:", error);
  }
}
document.querySelector('.li-hover-specialty').addEventListener('mouseleave', function () {
  document.querySelector('.dropdown-specialty').style.display = "none";
});

// Hiện dropdown khi rê chuột vào
document.querySelector('.li-hover-specialty').addEventListener('mouseenter', function () {
  showDropdownSpecialty();
});
document.querySelector('.dropdown-specialty').addEventListener('mouseenter', function () {
  showDropdownSpecialty();
});
document.querySelector('.dropdown-specialty').addEventListener('mouseleave', function () {
  document.querySelector('.dropdown-specialty').style.display = "none";
});



// window.load = load;
// document.addEventListener("DOMContentLoaded", load);

