window.addEventListener('load', e => { 
  reset();

  const selectElement = document.getElementById('create');

  selectElement.addEventListener('change', (e) => {
    reload();
  });

  document.getElementById('csubmit').addEventListener('click', e => { 
    e.preventDefault();
    let data = new FormData(document.getElementById('conferences')).entries();
    let formValues = [];
    for (let pair of data) {
      formValues.push(pair[1]);
    }
    createConference(formValues[0], formValues[1]);
  })

  document.getElementById('dsubmit').addEventListener('click', e => { 
    e.preventDefault();
    let data = new FormData(document.getElementById('divisions')).entries();
    let formValues = [];
    for (let pair of data) {
      formValues.push(pair[1]);
    }
    createDivision(formValues[0], formValues[1]);
  });

  document.getElementById('msubmit').addEventListener('click', e => { 
    e.preventDefault();
    let data = new FormData(document.getElementById('matches')).entries();
    let formValues = [];
    for (let pair of data) {
      formValues.push(pair[1]);
    }
    createMatch(formValues[0], formValues[1]);
  })

  document.getElementById('tsubmit').addEventListener('click', e => { 
    e.preventDefault();
    let data = new FormData(document.getElementById('teams')).entries();
    let formValues = [];
    for (let pair of data) {
      formValues.push(pair[1]);
    }
    createTeam(formValues[0], formValues[1], formValues[2]);
  })
});

function createConference(number, name) {
  fetch('http://localhost:8080/createConference', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      'conferenceNumber': number,
      'conferenceName': name
    })
  }).then(rsp => {
    reset();
  }).catch(e => {
    console.log(e);
  })
}

function createDivision(number, name) {
  fetch('http://localhost:8080/createDivision', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      'divisionNumber': number,
      'divisionName': name
    })
  }).then(rsp => { 
    reset();
  }).catch(e => {
    console.log(e);
  });
}

function createMatch(teamid1, teamid2) {
  fetch('http://localhost:8080/createMatch', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      'team1ID': teamid1,
      'team2ID': teamid2
    })
  }).then(rsp => {
    reset();
  }).catch(e => {
    console.log(e);
  })
}

function createTeam(name, conference, division) {
  fetch('http://localhost:8080/createTeam', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      'name': name,
      'conferenceNum': conference,
      'divisionNum': division
    })
  }).then(rsp => {
    reset();
  }).catch(e => {
    console.log(e);
  })
}


function reload() { 
  let formIDs = ['teams', 'conferences', 'divisions', 'matches'];
  for (let formID of formIDs) { 
    document.getElementById(formID).hidden = true;
    document.getElementById(formID).reset();
  }
  document.getElementById(document.getElementById("create").value).hidden = false;
}

function reset() { 
  let ctable = document.getElementById("ctable");
  for (let i = ctable.rows.length - 1; i > 0; i--) {
    ctable.deleteRow(i);
  }

  let dtable = document.getElementById("dtable");
  for (let i = dtable.rows.length - 1; i > 0; i--) {
    dtable.deleteRow(i);
  }

  let mtable = document.getElementById("mtable");
  for (let i = mtable.rows.length - 1; i > 0; i--) {
    mtable.deleteRow(i);
  }

  let ttable = document.getElementById("ttable");
  for (let i = ttable.rows.length - 1; i > 0; i--) {
    ttable.deleteRow(i);
  }

  loadConferences();
  loadDivisions();
  loadMatches();
  loadTeams();
}

function loadConferences() { 
  fetch("http://localhost:8080/conferences", {
  method: "GET",
  headers: { "Content-Type": "application/json" }
}).then(response =>
  {
    response = response.json().then(data => {
      const table = document.getElementById("ctable");
      data.forEach(item => { 
        let row = table.insertRow();
        let name = row.insertCell(0);
        name.innerHTML = item.conferenceName;
        let number = row.insertCell(1);
        number.innerHTML = item.conferenceNumber;
      })
    })
  });
}

function loadDivisions() { 
  fetch("http://localhost:8080/divisions", {
  method: "GET",
  headers: { "Content-Type": "application/json" }
}).then(response =>
  {
    response = response.json().then(data => { 
      const table = document.getElementById("dtable");
      data.forEach(item => { 
        let row = table.insertRow();
        let name = row.insertCell(0);
        name.innerHTML = item.divisionName;
        let number = row.insertCell(1);
        number.innerHTML = item.divisionNumber;
      })
    })
  });
}

function loadTeams() { 
  fetch("http://localhost:8080/teams", {
  method: "GET",
  headers: { "Content-Type": "application/json" }
}).then(response =>
  {
    response = response.json().then(data => { 
      const table = document.getElementById("ttable");
      data.forEach(item => { 
        let row = table.insertRow();
        let id = row.insertCell(0);
        id.innerHTML = item.id;
        let name = row.insertCell(1);
        name.innerHTML = item.name;
        
        let conference = row.insertCell(2);
        conference.innerHTML = item.conferenceNum;
        let division = row.insertCell(3);
        division.innerHTML = item.divisionNum;
        
      })
    })
  });
}

function loadMatches() { 
  fetch("http://localhost:8080/matches", {
  method: "GET",
  headers: { "Content-Type": "application/json" }
}).then(response =>
  {
    response = response.json().then(data => {
      const table = document.getElementById("mtable");
      data.forEach(item => { 
        let row = table.insertRow();
        let id = row.insertCell(0);
        id.innerHTML = item.matchID;
        let t1 = row.insertCell(1);
        t1.innerHTML = item.teamIDOne;
        let t2 = row.insertCell(2);
        t2.innerHTML = item.teamIDTwo;
      })
    })
  });
}