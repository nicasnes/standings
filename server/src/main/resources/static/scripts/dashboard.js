window.addEventListener('load', e => { 
  const selectElement = document.getElementById('create');

  selectElement.addEventListener('change', (event) => {
    let formIDs = ['teams', 'conferences', 'divisions', 'matches'];
    for (formID of formIDs) { 
      document.getElementById(formID).hidden = true;
    }
    document.getElementById(event.target.value).hidden = false;
  });

  document.getElementById('csubmit').addEventListener('click', () => { 
    let data = new FormData(document.getElementById('conferences')).entries();
    let formValues = [];
    for (let pair of data) {
      formValues.push(pair[1]);
    }
    createConference(formValues[0], formValues[1]);
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
    console.log(rsp);
  });
}

