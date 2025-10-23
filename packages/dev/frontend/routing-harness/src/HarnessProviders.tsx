import { ApolloClient, ApolloLink, ApolloProvider, InMemoryCache, Observable } from '@apollo/client';
import { Selection, ToastContext } from '@eclipse-sirius/sirius-components-core';
import { ReactNode, useMemo } from 'react';
import { DiagramContext } from '../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/DiagramContext';
import type { DiagramContextValue } from '../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/DiagramContext.types';
import type { ToastContextValue } from '@eclipse-sirius/sirius-components-core';

const createApolloClient = () =>
  new ApolloClient({
    cache: new InMemoryCache(),
    link: new ApolloLink(() => new Observable((observer) => observer.complete())),
    defaultOptions: {
      mutate: {
        errorPolicy: 'ignore',
      },
    },
  });

const apolloClient = createApolloClient();

type HarnessProvidersProps = {
  children: ReactNode;
};

export const HarnessProviders = ({ children }: HarnessProvidersProps) => {
  const toastContextValue = useMemo<ToastContextValue>(
    () => ({
      enqueueSnackbar: () => {},
    }),
    []
  );

  const diagramContextValue = useMemo<DiagramContextValue>(
    () => ({
      editingContextId: 'routing-harness',
      diagramId: 'routing-harness',
      readOnly: true,
      registerPostToolSelection: () => {},
      consumePostToolSelection: () => null,
      toolSelections: new Map<string, Selection>(),
    }),
    []
  );

  return (
    <ApolloProvider client={apolloClient}>
      <ToastContext.Provider value={toastContextValue}>
        <DiagramContext.Provider value={diagramContextValue}>{children}</DiagramContext.Provider>
      </ToastContext.Provider>
    </ApolloProvider>
  );
};
