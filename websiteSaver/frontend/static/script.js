const RSAEncrypt = new JSEncrypt();
/** outer div for the list of job  */
const jobListDiv = document.getElementById("jobListDiv");
/** public key for RSA encryption */
let publicKey = null;

function hasPubKey() {
  if (publicKey) return true;
  else return false;
}

function fetchRsaPubKey() {
  fetch("/key/public", {
    method: "GET",
  }).then((response) => {
    response.text().then((txt) => {
      publicKey = txt;
      console.log("pubkey", publicKey);
    });
  });
}

function encrypt(text) {
  if (!text) {
    return null;
  }
  RSAEncrypt.setPublicKey(publicKey);
  return RSAEncrypt.encrypt(text);
}

function grabWithChrome() {
  if (!hasPubKey()) {
    fetchRsaPubKey();
    return;
  }

  let urlEle = document.getElementById("urlInputC");
  let url = urlEle.value;
  let path = document.getElementById("targetInputC").value;

  if (!validate(url)) return;

  fetch("/download/with/chrome", {
    method: "POST",
    body: JSON.stringify({
      url: encrypt(url),
      path: encrypt(path),
    }),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  }).then((response) => {
    if (response.status === 200) {
      window.alert("Success!");
      urlEle.value = null;
    } else {
      window.alert("Failed!");
    }
  });
}

function validate(url) {
  if (!url) {
    window.alert("Url shouldn't be empty!");
    return false;
  }
  return true;
}

function fetchJobList() {
  fetch("/job/list", {
    method: "GET",
  })
    .then((response) => response.json())
    .then((result) => {
      if (!result) {
        console.error("Error, returned result: ", result);
        return;
      }

      const jobInfoList = result;
      for (let jobInfo of jobInfoList) {
        let li = document.createElement("li");
        let innerLink = document.createElement("a");
        innerLink.textContent = jobInfo;
        li.appendChild(innerLink);
        li.classList.add("list-group-item");
        li.classList.add("list-group-item-action");
        li.setAttribute("target", "_blank");
        li.style.wordBreak = "break-all";
        jobListDiv.appendChild(li);
      }
    })
    .catch((error) => {
      console.error(error);
    });
}

function logout() {
  window.location = "/logout";
}

// -------------------------------- main -----------------------------------

// download public key for encryption
fetchRsaPubKey();
fetchJobList();
