// Registration Form Submit Loader
window.onload = function load() 
{
	document.getElementById('verified').style.display = 'none';
}

setTimeout (function(){
        document.getElementById('submitButton').disabled = null;
        },31200);

        var countdownNum = 30;
        incTimer();

        function incTimer()
        {
        setTimeout (function()
        {
            if(countdownNum != 0)
            {
	            countdownNum--;
	            document.getElementById('timeLeft').innerHTML = 'Resend after: ' + countdownNum + ' seconds';
	            incTimer();
            } 
            else
            {
    			document.getElementById('timeLeft').innerHTML = 'Resend Now!';
            }
        },1000);
        }
         