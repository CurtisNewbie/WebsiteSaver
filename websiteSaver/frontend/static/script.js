const URL_FOR_ITEXT = "/download/with/itext";
const URL_FOR_CHROME = "/download/with/chrome";
const URL_FOR_PUB_KEY = "/key/public";
const RSAEncrypt = new JSEncrypt();
let publicKey = null;

// download public key for encryption
downloadPubKey();

function hasPubKey() {
  if (publicKey) return true;
  else return false;
}

function downloadPubKey() {
  fetch(URL_FOR_PUB_KEY, {
    method: "GET",
  }).then((response) => {
    response.text().then((txt) => {
      publicKey = txt;
      console.log("pubkey", publicKey);
    });
  });
}

function encrypt(text) {
  if (!text) return null;
  RSAEncrypt.setPublicKey(publicKey);
  return RSAEncrypt.encrypt(text);
}

function grabWithChrome() {
  if (!hasPubKey()) {
    downloadPubKey();
    return;
  }

  let url = document.getElementById("urlInputC").value;
  let path = document.getElementById("targetInputC").value;
  let authKey = document.getElementById("authKeyIn").value;

  if (!validate(url, path, authKey)) return;

  fetch(URL_FOR_CHROME, {
    method: "POST",
    body: JSON.stringify({
      url: encrypt(url),
      path: encrypt(path),
      authKey: encrypt(Date.now() + "::" + authKey),
    }),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  }).then((response) => {
    if (response.status === 200) {
      window.alert("Success!");
    } else {
      window.alert("Failed!");
    }
  });
}

function grabWithItext() {
  if (!hasPubKey()) {
    downloadPubKey();
    return;
  }

  let url = document.getElementById("urlInputI").value;
  let path = document.getElementById("targetInputI").value;
  let authKey = document.getElementById("authKeyIn").value;

  if (!validate(url, path, authKey)) return;

  fetch(URL_FOR_ITEXT, {
    method: "POST",
    body: JSON.stringify({
      url: encrypt(url),
      path: encrypt(path),
      authKey: encrypt(Date.now() + "::" + authKey),
    }),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  });
  window.alert("Success!");
}

function validate(url, path, authKey) {
  if (!url) {
    window.alert("Url shouldn't be empty!");
    return false;
  }
  if (!authKey) {
    window.alert("Enter your password first!");
    return false;
  }
  if (authKey.indexOf(":") > 0) {
    window.alert("Password doesn't allow ':' !");
    return false;
  }
  return true;
}
