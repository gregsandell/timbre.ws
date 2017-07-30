// Timeout Object
// Constructor
Timeout = function(name) {
	this.name=name;
	this.clockDiv=null;
	this.clockContainer = null;
	this.politeMessage = null;
	this.suddenDeathMessage = null;
	this.timeOutPopup = null;
	this.timeOutPopupText = null;
	this.timeout
	this.msgCtrl = null;
	this.alertWin=null;
	this.interval = 15;
	this.warningPeriod = 5;
	this.suddenDeathPeriod = 1;
	this.state = 0;
	this.timer = null;
	this.logoutURL = null;
	this.confirms = 0;
}
Timeout.prototype = {
	openAlert: function() {
		var aw = window.open('','Alert','resizable,top=300,left=300,width=210,height=170');
		var ad = aw.document;
		var str = '<html>'
		str += '<head>'
		str += '<title>' + 'Warning' + '</title>'
		str += '</head>'
		str += '<body bgcolor="#0099FF" text="#FFFFFF">'
		str += '<font size=2>'
		str += 'Time out warning';
		str += '<p>';
		str += 'Your page has been inactive for 10 minutes and will time out in 5 more minutes.';
		str += '</font>'
		str += '</body>'
		str += '</html>'
		ad.write(str);
		ad.close();
		this.alertWin = aw;
	},
	alertAndClose: function() {
		this.closeAlert();
		document.location = this.logoutURL;
	},
	closeAlert: function() {
		if (this.alertWin != null) {
			this.alertWin.close();
		}
	},
	formatDisplay: function(m,s) {
	   var fs = "";
	   if (m<0) m=0;
	   if (m<=9) fs+="0";
	   fs+=m;
	   fs+=":";
	   if (s<0) s=0;
	   if (s<=9) fs+="0";
	   fs+=s;
	   return fs;
	},
	stopWatch: function(m,s) {
		s--;
		if (s == -1) { 
			s = 59; 
			m--; 
		}
		if (m <= (this.warningPeriod - 1)) {
			/*
			this.msgCtrl.innerHTML = "Your page will expire in under " + (m + 1) + " minutes";
			if (this.state == 0) {
					this.clockDiv.style.color = "red";
					this.state = 1;
			}
				else if (this.state == 1) {
					this.clockDiv.style.color = "white";
					this.state = 0;
				}
			*/
			this.clockContainer.style.display = 'block';
			if (this.confirms < 1) {
				this.timeOutPopup.style.display = 'block';
				this.timeOutPopupText.innerHTML = this.politeMessage; 
				++this.confirms;
			}
		}
		if (m <= (this.suddenDeathPeriod - 1)) {
			if (this.confirms < 2) {
				this.timeOutPopup.style.display = 'block';
				this.timeOutPopupText.innerHTML = this.suddenDeathMessage; 
				++this.confirms;
			}
		}
		this.clockDiv.style.color = "red";
		this.clockDiv.innerHTML = this.formatDisplay(m,s);
		
		//Stop the stop watch if the time has expired.
		if(m == 0 && s == 0) {
			// this.msgCtrl.innerHTML = "Logging you out..." + this.logoutURL;
			window.location.href = this.logoutURL;
			return;
		}
		this.timer = setTimeout(this.name+".stopWatch(" + m + "," + s + ")", 1000); 
	},
	init: function() {
		if (this.clockDiv != null) {
			this.stopWatch(this.interval,0);
		}	
	},
	reset: function() {
		clearTimeout(this.timer);
		this.msgCtrl.innerHTML = "";
		this.state = 1;
		this.stopWatch(this.interval, 0);		
	},

	hidePopup: function() {
		this.timeOutPopup.style.display = 'none';
	}
}
