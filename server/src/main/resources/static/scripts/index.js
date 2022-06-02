fetch("http://localhost:8080/count", {
  method: "GET",
  headers: { "Content-Type": "application/json" }
}).then(response =>
  {
    console.log(response);
    response = response.json().then(data => { 
      console.log(data);
      document.getElementById('count').innerHTML = data + ".";
    })
  });