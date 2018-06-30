from django.db import models

class Content(models.Model):
    id = models.AutoField(primary_key=True)
    userID = models.CharField(max_length=100, blank=True, default='')
    showName = models.CharField(max_length=100, blank=True, default='')
    showTitle = models.CharField(max_length=100, blank=True, default='')
    showLocation = models.CharField(max_length=100, blank=True, default='')
    showGenre = models.PositiveSmallIntegerField(default=0)
    showHeart = models.PositiveSmallIntegerField(default=0)
    showTime = models.CharField(max_length=100, blank=True, default='')
    showDescription = models.CharField(max_length=100, blank=True, default='')


class Signup(models.Model):
    userId : models.CharField(max_length=100, blank=True, default='', primary_key=True)
    userPw : models.CharField(max_length=100, blank=True, default='')
    pwCheck : models.CharField(max_length=100, blank=True, default='')
    name : models.CharField(max_length=100, blank=True, default='')
    mail : models.CharField(max_length=100, blank=True, default='')
    phone : models.CharField(max_length=100, blank=True, default='')
