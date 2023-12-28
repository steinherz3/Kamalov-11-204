function addAccount(name, email, phoneNumber) {
    // hollow strings validation
  
    if (name === '' || email === '' || phoneNumber === '') {
      alert('Please enter all fields');
      return;
    }
  
  
    // corectness data validation
  
    
    if (!isEmailValid(email)) {
      alert('Check your email validity please');
      return;
    }
    
    if (!isPhoneNumberValid(phoneNumber)) {
      alert('Check your phone validity please');
      return;
    }
    
    if (!isNameValid(name)) {
      alert('Check your name validity please');
      return;
    }  
    // add Account into table
  
    const table = document.getElementById('accountData').getElementsByTagName('tbody')[0];
    const newAccount = table.insertRow(table.rows.length);
    const firstColumn = newAccount.insertCell(0);
    const secondColumn = newAccount.insertCell(1);
    const thirdColumn = newAccount.insertCell(2);
  
    firstColumn.innerHTML = name;
    secondColumn.innerHTML = email;
    thirdColumn.innerHTML = phoneNumber;
  
    document.getElementById('name').value = '';
    document.getElementById('email').value = '';
    document.getElementById('phone').value = '';

    alert('Added new Account!')
    return;
  }
  
  function isEmailValid(email) {
    const emailValidationPattern = /^\S+@\S+\.\S+$/; // WARNING! validation doesn't check domain scope
    return emailValidationPattern.test(email);
  }
  
  function isPhoneNumberValid(phone) {
    // Validation works only for russian numbers
    const phoneValidationPattern = /(?:\+|\d)[\d\-\(\) ]{9,}\d/g;
    return phoneValidationPattern.test(phone);
  }
  function isNameValid(name) {
    const nameValidationPattern = /^[A-Z][a-z]*$/;
    return nameValidationPattern.test(name);
  }
  function checkPalindrome(word) {

    let text = word.toLowerCase();

    const isPalindrome = text == text.split('').reverse().join('');

    if(isPalindrome) {
        document.getElementById('palindromeResult').innerText = 'This text is a palindrome.';
    }
    else{
        document.getElementById('palindromeResult').innerText = 'This text is not a palindrome.';
    }

 }