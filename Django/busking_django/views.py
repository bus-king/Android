from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from busking_django.models import Event  
from busking_django.serializers import EventSerializer

# 요청 url 인 bbs/ 에 대해서 urls.py 에 정의된 view.bbs_list 가 호출된다.
@api_view(['GET', 'POST'])
def event_list(request, format=None):  
    if request.method == 'GET':
        event = Event.objects.all()
        serializer = EventSerializer(event, many=True) # many 값이 True 이면 다수의 데이터 instance를 직렬화할수 있다
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = EventSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

# 요청 url 인 bbs/번호 에 대해서 urls.py 에 정의된 view.bbs_detail 이 호출된다
@api_view(['GET', 'PUT', 'DELETE'])
def event_detail(request, pk, format=None):
    try:
        event = Event.objects.get(pk=pk)
    except Event.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer = EventSerializer(bbs)
        return Response(serializer.data)

    elif request.method == 'PUT':
        serializer = EventSerializer(event, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'DELETE':
        event.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
