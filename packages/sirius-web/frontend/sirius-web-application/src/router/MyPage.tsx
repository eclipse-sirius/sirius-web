import { gql, OnDataOptions, useSubscription } from '@apollo/client';
import { diagramEventSubscription } from '@eclipse-sirius/sirius-components-diagrams';
import {
  GQLDiagramEventData,
  GQLDiagramEventVariables,
} from '@eclipse-sirius/sirius-components-diagrams/dist/representation/DiagramRepresentation.types';

const subscription = gql(diagramEventSubscription);

export const MyPage = () => {
  const variables: GQLDiagramEventVariables = {
    input: {
      id: crypto.randomUUID(),
      editingContextId: 'a6928a7f-ed7d-4df5-bb20-208c3e927461',
      diagramId: 'd7d312a7-4a8f-4844-89be-21049c04d79c',
    },
  };

  const onData = ({ data }: OnDataOptions<GQLDiagramEventData>) => {
    if (data.data) {
      const { diagramEvent } = data.data;
      console.log(diagramEvent);
    }
  };

  const onComplete = () => {
    console.log('complete');
  };

  const { error } = useSubscription<GQLDiagramEventData>(subscription, {
    variables,
    fetchPolicy: 'no-cache',
    onData,
    onComplete,
  });

  if (error) {
    return <div>{error.message}</div>;
  }

  return <div>Received</div>;
};
