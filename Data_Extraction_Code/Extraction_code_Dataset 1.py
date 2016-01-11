import feedparser
import time
import imdb
import sys
import csv

from subprocess import check_output

#url = "http://rss.imdb.com/list/ls003857730/"
#feed = feedparser.parse(url)
file1 = open("test_all5.csv", 'rU')
#reader = csv.reader(open("test_all1.csv", 'rU'), dialect=csv.excel_tab)
reader = csv.reader(file1)

ia = imdb.IMDb()

#csvout = csv.writer(open("mydata_newFinal.csv", "ab"))
csvout = csv.writer(open("theData.csv", "ab"))
#csvout.writerow(printinfo.encode('utf8') + '\n')
#csvout.writerow(("movie_id","title","genre0","genre1","year","cast0","cast1","director","writer","runtime","kind","distributors","languages","countries","rating","producer"))

def find_between( s, first, last ):
    try:
        start = s.index( first ) + len( first )
        end = s.index( last, start )
        return s[start:end]
    except ValueError:
        return ""
        
itercars = iter(reader)
next(itercars)

for row in itercars:
	title = row[5]
	link = row[14]
	print link
	id = find_between(link,"/tt", "/" )
	print id
	s_result = ia.get_movie(id)
	try:
		title = s_result['title']
	except Exception, e:
		title = "NA"
	try:
		genre0 = s_result['genres'][0]
	except Exception, e:
		genre0 = "NA"
	try:
		genre1 = s_result['genres'][1]
	except Exception, e:
		genre1 = genre0	
	try:
		cast0 = s_result['cast'][0]
	except Exception, e:
		cast0 = "NA"
	try:
		cast1 = s_result['cast'][1]
	except Exception, e:
		cast1 = "NA"
	try:
		director = s_result['director'][0]['name']
	except Exception, e:
		director = "NA"
	try:
		writer = s_result['writer'][0]['name']
	except Exception, e:
		writer = "NA"
	try:
		distributors = s_result['distributors'][0]['name']
	except Exception, e:
		distributors = "NA"
	try:
		languages = s_result['languages'][0]
	except Exception, e:
		languages = "NA"
	try:
		countries = s_result['countries'][0]
	except Exception, e:
		countries = "NA"
	try:
		year = s_result['year']
	except Exception, e:
		year = "NA"
	try:
		kind = s_result['kind']
	except Exception, e:
		kind = "NA"
	try:
		movie_id = id
	except Exception, e:
		movie_id = "NA"
	try:
		rating = s_result['rating']
	except Exception, e:
		rating = "NA"
	try:
		producer = s_result['producer'][0]['name']
	except Exception, e:
		producer = "NA"	
	
	#runtime = s_result['runtimes'][0]
	runtime = row[10]
	
	
	print title
	print genre0
	print genre1
	print year
	print cast0
	print cast1
	print director
	print writer
	print runtime
	print kind
	print distributors
	print languages
	print countries
	print rating
	print producer
	 
	csvout.writerow((unicode(movie_id).encode("utf-8"),unicode(title).encode("utf-8"),unicode(genre0).encode("utf-8"),unicode(genre1).encode("utf-8"),year,unicode(cast0).encode("utf-8"),unicode(cast1).encode("utf-8"),unicode(director).encode("utf-8"),unicode(writer).encode("utf-8") ,runtime,kind,unicode(distributors).encode("utf-8"),unicode(languages).encode("utf-8"),countries,unicode(rating).encode("utf-8"),unicode(producer).encode("utf-8")))
	#csvout.writerow((title..encode('utf8'), genre, year,cast0.encode('utf8'),cast1.encode('ascii', 'xmlcharrefreplace'),director.encode('ascii', 'xmlcharrefreplace'),writer.encode('ascii', 'xmlcharrefreplace'),runtime,kind,distributors.encode('ascii', 'xmlcharrefreplace'),languages,countries))
    #csvout.writerow((unicode(title).encode("utf-8"),unicode(genre).encode("utf-8"),year,unicode(cast0).encode("utf-8"),unicode(cast1).encode("utf-8"),unicode(director).encode("utf-8"),unicode(writer).encode("utf-8") ,runtime,kind,unicode(distributors).encode("utf-8"),languages,countries)) 
file1.close()    

