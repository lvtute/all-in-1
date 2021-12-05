import { Card, Container, Row } from "react-bootstrap";

const QuestionManager = () => {
  return (
    <>
      <h4>Câu hỏi dành cho tôi</h4>
      <Container>
        <Row>
          <Card style={{ width: "100%", padding: "1% 2%" }}>
            <Card.Body style={{padding:"0"}}>
              <Card.Title>Card Title</Card.Title>
              <Card.Subtitle className="mb-2 text-muted">
                Card Subtitle
              </Card.Subtitle>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </>
  );
};
export default QuestionManager;
