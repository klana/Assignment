<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head><title>
	RAD PDF - The ASP.NET AJAX PDF Viewer and PDF Editor
</title><link rel="shortcut icon" href="favicon.ico" type="image/x-icon" /><link rel="icon" href="favicon.ico" type="image/x-icon" /><link rel="stylesheet" type="text/css" href="css/csharp.css" /><link rel="stylesheet" type="text/css" href="css/web6.css" /><link rel="alternate" type="application/rss+xml" href="http://feeds.feedburner.com/pdfescape" /><meta name="author" content="Red Software" /><meta name="copyright" content="Copyright Red Software" /><meta name="robots" content="index, follow" />

	<script type="text/javascript">
	//<![CDATA[
	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-7186015-3']);
	_gaq.push(['_trackPageview']);

	(function() {
		var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	})();
	//]]>
	</script>

	<script type="text/javascript" src="/js/jquery-1.6.1.min.js"></script>
	<script type="text/javascript" src="/js/jquery.simpletip-1.3.1.js"></script>
	<script type="text/javascript">
	//<![CDATA[
	$(document).ready(function() {
		$(".addTooltipLeft").each(function() {
			var s = $(this).attr("title");
			if(s)
			{
				$(this).simpletip({
					baseClass : "tooltipLeft",
					content : "<div>" + s + "</div>",
					position: "left"
				});
				$(this).removeAttr("title");
			}
		});
		$(".addTooltipRight").each(function() {
			var s = $(this).attr("title");
			if(s)
			{
				$(this).simpletip({
					baseClass : "tooltipRight",
					content : "<div>" + s + "</div>",
					position: "right"
				});
				$(this).removeAttr("title");
			}
		});
	});
	//]]>
	</script>

	
  <meta name="description" content="RAD PDF - the ASP.NET PDF Reader &amp; PDF Editor - tightly integrates PDF technology into your ASP.NET Web Forms and MVC web application. No Adobe Acrobat, Flash, or ActiveX." />
</head>
<body>
	<a id="top"></a>
	<div id="pBody">
		<div id="pHead">
			<div style="float:left;">
				<a href="/"><img src="/images/rad_pdf_header.gif" alt="RAD PDF - PDF Viewer &amp; Editor for ASP.NET" width="500" height="60" /></a>
			</div>
		</div>
		<div id="pNav">
			<table cellspacing="0" cellpadding="0">
				<tr>
					<td><a href="/features/" title="RAD PDF Viewer &amp; Editor Features">Feature Overview</a></td>
					<td><a href="/demo/" title="Live RAD PDF Code Samples &amp; Demonstrations">Live Demo</a></td>
					<td><a href="/install/" title="RAD PDF Downloads &amp; Installation">Download</a></td>
					<td><a href="/support/" title="RAD PDF Technical Support">Support</a></td>
					<td><a href="/purchase/" title="RAD PDF License Pricing &amp; Purchasing">Pricing &amp; Purchasing</a></td>
					<td><a href="http://www.redsoftware.com/contact/" title="Contact Red Software">Contact Us</a></td>
				</tr>
			</table>
		</div>

		<div id="pLeft" style="width:100%;">
			

<table border="0" cellpadding="2" cellspacing="2" style="border:0px none;margin:10px auto;">
<tr>
<td style="font-size:13px;text-align:center;width:350px;">
	<a href="/features/">
	    <img src="/images/rad_pdf_logo.jpg" width="325" height="325" alt="RAD PDF - PDF Editor for ASP.NET" style="border:0px none" />
	</a>
	<div>The ASP.NET AJAX PDF Viewer &amp; PDF Editor</div>
</td>
<td class="rOverview" style="width:500px;">
	<a class="addTooltipLeft" href="/features/#reader" 
	    title="A 100% HTML based PDF viewer supported by 99% of internet browsers which doesn&#39;t sacrifice PDF features (e.g. hyperlinks &amp; bookmarks). &lt;br /&gt;&lt;b&gt;No Adobe Acrobat Reader! &lt;br /&gt;No Adobe Flash! &lt;br /&gt;No Plugins!&lt;/b&gt;">
	    HTML Based PDF Reader</a>
	<a class="addTooltipLeft" href="/features/#editor" 
	    title="Offer &amp; scope PDF editing tools unavailable in Adobe Reader. &lt;br /&gt;&lt;b&gt;Add text, graphics, annotations, links, &amp; more!&lt;/b&gt;">
	    Client-Side PDF Editor</a>
	<a class="addTooltipLeft" href="/features/#filler" 
	    title="Collect PDF form data quickly &amp; easily from website users with your pre-existing PDF forms. &lt;br /&gt;&lt;b&gt;Complete, pre-populate, save, &amp; print PDF forms.&lt;/b&gt;">
	    Feature Rich PDF Form Filler</a>
	<a class="addTooltipLeft" href="/features/#designer" 
	    title="Design PDF forms intuitively in a web browser with a familiar GUI.">
	    Interactive PDF Form Designer</a>
	<a class="addTooltipLeft" href="/features/#secure" 
	    title="Display a PDF document confidently while preventing your valuable PDF data from ever being downloaded or printed.">
	    Protect &amp; Secure PDF Content</a>
	<a class="addTooltipLeft" href="/features/#integrate" 
	    title="Control how &amp; where PDF documents are displayed, interacted with, form filled, saved, &amp; printed.">
	    Integrate PDF Into Your Workflow</a>
	<a class="addTooltipLeft" href="/features/#aspnet" 
	    title="Ready out of the box, RAD PDF can be deployed in minutes.">
	    Enterprise Ready ASP.NET Control</a>
</td>
</tr>
</table>

<table border="0" cellpadding="2" cellspacing="2" style="margin:30px auto;width:80%;">
<tr>
<td style="width:50%;">
	<a class="rButton175" href="/demo/" title="See live demonstrations of RAD PDF">See It In Action</a>
</td>
<td style="width:50%;">
	<a class="rButton175" href="/install/" title="Download an evaluation copy of RAD PDF">Get Started Now</a>
</td>
</tr>
</table>
    

		</div>
		
		<div id="pFoot">
			<a href="http://www.redsoftware.com/terms/" target="_blank">Terms of Use</a> | <a href="http://www.redsoftware.com/privacy/" target="_blank">Privacy</a><br/>
			RAD PDF &amp; PDFescape are <a href="http://www.redsoftware.com" target="_blank">Red Software</a> products - &copy;2007-2015 Red Software
		</div>
	</div>
</body>
</html>
