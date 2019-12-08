importScripts('https://www.gstatic.com/firebasejs/5.5.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/5.5.2/firebase-messaging.js');

firebase.initializeApp({
    'messagingSenderId': '' //이곳은 자신의 프로젝트 설정 => 클라우드 메세징 => 발신자ID를 기입
});

self.addEventListener('push', function(event) {
    const payload = event.data.json();
    const title = payload.notification.title;
    const options = {
        body: payload.notification.body,
        icon: payload.notification.icon,
        data: '/'
    };

    event.waitUntil(self.registration.showNotification(title, options));
});

self.addEventListener('notificationclick', function(event) {
    console.log(event.notification);
    event.notification.close();
    event.waitUntil(
        clients.openWindow(event.notification.data)
    );
});