# Generated by Django 2.0.6 on 2018-06-29 19:11

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('busking_django', '0004_auto_20180629_1013'),
    ]

    operations = [
        migrations.CreateModel(
            name='Content',
            fields=[
                ('id', models.IntegerField(primary_key=True, serialize=False)),
                ('userID', models.CharField(blank=True, default='', max_length=100)),
                ('showName', models.CharField(blank=True, default='', max_length=100)),
                ('showTitle', models.CharField(blank=True, default='', max_length=100)),
                ('showLocation', models.CharField(blank=True, default='', max_length=100)),
                ('showGenre', models.PositiveSmallIntegerField(default=0)),
                ('showHeart', models.PositiveSmallIntegerField(default=0)),
                ('showTime', models.CharField(blank=True, default='', max_length=100)),
                ('showDescription', models.CharField(blank=True, default='', max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='Signup',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
            ],
        ),
        migrations.DeleteModel(
            name='Event',
        ),
    ]