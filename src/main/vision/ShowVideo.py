import cv2

cap = cv2.VideoCapture("http://frcvision.local:1181/?action=stream")
cap.set(3,640)
cap.set(4,380)

while True:
    success, img = cap.read()
    img = cv2.resize(img, (1280,760))
    cv2.imshow("Press 'esc' or 'q' to quit", img)
    if cv2.waitKey(1) & 0xFF == 27 or cv2.waitKey(1) & 0xFF ==ord('q'):
        break
cv2.destroyAllWindows()
