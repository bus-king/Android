from django.db import models

class Event(models.Model):

    id = models.IntegerField(primary_key=True)
    userID = models.CharField(max_length=100, blank=True, default='')
    showName = models.CharField(max_length=100, blank=True, default='')
    showTitle = models.CharField(max_length=100, blank=True, default='')
    showLocation = models.CharField(max_length=100, blank=True, default='')
    showGenre = models.PositiveSmallIntegerField(default=0)
    showHeart = models.PositiveSmallIntegerField(default=0)
    showTime = models.CharField(max_length=100, blank=True, default='')
    showDescription = models.CharField(max_length=100, blank=True, default='')
    created = models.DateTimeField(auto_now_add=True)

    class Meta:
        ordering = ('created',)
