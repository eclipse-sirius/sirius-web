/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { gql, useMutation, useSubscription } from '@apollo/client';
import {
  DRAG_SOURCES_TYPE,
  Representation,
  RepresentationComponentProps,
  Selection,
  SelectionContext,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';
import { useCallback, useState } from 'react';
import {
  GQLAddPortalViewMutationData,
  GQLAddPortalViewMutationVariables,
  GQLPortalEventPayload,
  GQLPortalEventSubscription,
  GQLPortalEventVariables,
  GQLPortalRefreshedEventPayload,
  GQLPortalView,
  GQLRemovePortalViewMutationData,
  GQLRemovePortalViewMutationVariables,
  PortalRepresentationState,
} from './PortalRepresentation.types';
import { RepresentationFrame } from './RepresentationFrame';

const portalEventSubscription = gql`
  subscription portalEvent($input: PortalEventInput!) {
    portalEvent(input: $input) {
      __typename
      ... on PortalRefreshedEventPayload {
        id
        portal {
          id
          targetObjectId
          views {
            id
            representationMetadata {
              id
              kind
              label
            }
          }
        }
      }
    }
  }
`;

const addPortalViewMutation = gql`
  mutation addPortalView($input: AddPortalViewInput!) {
    addPortalView(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const removePortalViewMutation = gql`
  mutation removePortalView($input: RemovePortalViewInput!) {
    removePortalView(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

interface PortalStyleProps {
  rows: number;
}

const usePortalStyles = makeStyles((theme) => ({
  portalRepresentationArea: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: ({ rows }: PortalStyleProps) => `min-content repeat(${rows}, 1fr)`,
  },
  dropArea: {
    width: theme.spacing(100),
    height: theme.spacing(10),
    justifySelf: 'center',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    border: '1px dashed',
    borderColor: theme.palette.grey[400],
    margin: theme.spacing(2),
  },
}));

const isPortalRefreshedEventPayload = (payload: GQLPortalEventPayload): payload is GQLPortalRefreshedEventPayload =>
  payload.__typename === 'PortalRefreshedEventPayload';

export const PortalRepresentation = ({
  editingContextId,
  representationId,
  readOnly,
}: RepresentationComponentProps) => {
  const [state, setState] = useState<PortalRepresentationState>({
    id: crypto.randomUUID(),
    portalRefreshedEventPayload: null,
    complete: false,
    message: null,
  });

  const variables: GQLPortalEventVariables = {
    input: {
      id: state.id,
      editingContextId,
      portalId: representationId,
    },
  };

  const { error } = useSubscription<GQLPortalEventSubscription, GQLPortalEventVariables>(portalEventSubscription, {
    variables,
    fetchPolicy: 'no-cache',
    onData: ({ data }) => {
      if (data.data) {
        const { portalEvent } = data.data;
        if (isPortalRefreshedEventPayload(portalEvent)) {
          setState((prevState) => ({ ...prevState, portalRefreshedEventPayload: portalEvent }));
        }
      }
    },
    onComplete: () => {
      setState((prevState) => ({ ...prevState, portalRefreshedEventPayload: null, complete: true }));
    },
  });

  const [addPortalView] = useMutation<GQLAddPortalViewMutationData, GQLAddPortalViewMutationVariables>(
    addPortalViewMutation
  );

  const [removePortalView] = useMutation<GQLRemovePortalViewMutationData, GQLRemovePortalViewMutationVariables>(
    removePortalViewMutation
  );

  const handleDeleteView = (view: GQLPortalView) => {
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      portalViewId: view.id,
    };
    removePortalView({ variables: { input } });
  };

  const handleDrop = (event) => {
    event.preventDefault();
    const dragSourcesStringified = event.dataTransfer.getData(DRAG_SOURCES_TYPE);
    if (dragSourcesStringified) {
      const sources = JSON.parse(dragSourcesStringified);
      if (Array.isArray(sources)) {
        const sourceIds = sources.filter((source) => source?.id).map((source) => source.id);
        if (sourceIds.length > 0) {
          const input = {
            id: crypto.randomUUID(),
            editingContextId,
            representationId,
            viewRepresentationId: sourceIds[0],
          };
          addPortalView({ variables: { input } });
        }
      }
    }
  };

  const handleDragOver = (event) => {
    event.preventDefault();
    const dataTransferItems = [...event.dataTransfer.items];
    const sourcesItem = dataTransferItems.find((item) => item.type !== DRAG_SOURCES_TYPE);
    if (sourcesItem) {
      // Update the cursor thanks to dropEffect (a drag'n'drop cursor does not use CSS rules)
      event.dataTransfer.dropEffect = 'link';
    }
  };

  const handleDragLeave = (event) => {
    event.preventDefault();
  };

  const { selection, setSelection } = useSelection();

  const nonPropagatingSetSelection = useCallback(
    (selection: Selection) => {
      const filteredEntries = selection.entries.filter(
        (entry) => !entry.kind.startsWith('siriusComponents://representation')
      );
      if (filteredEntries.length > 0) {
        setSelection({ entries: filteredEntries });
      }
    },
    [setSelection]
  );

  let content: JSX.Element | null = null;
  let rows = 1;
  const views = state.portalRefreshedEventPayload?.portal.views;
  if (views) {
    const representations: Representation[] = views.map((view) => {
      return {
        id: view.representationMetadata.id,
        label: view.representationMetadata.label,
        kind: view.representationMetadata.kind,
      };
    });
    rows = representations.length;
    content = (
      <SelectionContext.Provider value={{ selection, setSelection: nonPropagatingSetSelection }}>
        {representations
          .filter((r) => r.id !== representationId)
          .map((r) => (
            <RepresentationFrame
              key={r.id}
              editingContextId={editingContextId}
              readOnly={readOnly}
              representation={r}
              onDelete={() =>
                views
                  ?.filter((view) => view.representationMetadata.id === r.id)
                  .forEach((view) => handleDeleteView(view))
              }
            />
          ))}
      </SelectionContext.Provider>
    );
  } else {
    content = <div />;
  }

  const classes = usePortalStyles({ rows });

  if (state.message) {
    return <div>{state.message}</div>;
  }
  if (error) {
    return <div>{error.message}</div>;
  }
  if (state.complete) {
    return <div>The representation is not available anymore</div>;
  }
  if (!state.portalRefreshedEventPayload) {
    return <div></div>;
  }

  return (
    <div className={classes.portalRepresentationArea}>
      <div
        className={classes.dropArea}
        onDrop={(event) => handleDrop(event)}
        onDragOver={(event) => handleDragOver(event)}
        onDragLeave={(event) => handleDragLeave(event)}>
        <AddIcon fontSize={'large'} />
        <Typography variant="caption">Drop representations here to add them to the portal</Typography>
      </div>
      {content}
    </div>
  );
};
