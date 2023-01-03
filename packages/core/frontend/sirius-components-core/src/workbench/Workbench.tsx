/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import { gql, useSubscription } from '@apollo/client';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import React, { useContext, useEffect } from 'react';
import { useSelection } from '../useSelection';
import { Panels } from './Panels';
import { RepresentationContext } from './RepresentationContext';
import { RepresentationNavigation } from './RepresentationNavigation';
import {
  GQLEditingContextEventSubscription,
  Representation,
  RepresentationComponentProps,
  WorkbenchProps,
} from './Workbench.types';
import {
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideRepresentationEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  UpdateSelectionEvent,
  WorkbenchContext,
  WorkbenchEvent,
  workbenchMachine,
} from './WorkbenchMachine';
import { WorkbenchViewContribution } from './WorkbenchViewContribution';

const editingContextEventSubscription = gql`
  subscription editingContextEvent($input: EditingContextEventInput!) {
    editingContextEvent(input: $input) {
      __typename
      ... on RepresentationRenamedEventPayload {
        representationId
        newLabel
      }
    }
  }
`;

const useWorkbenchStyles = makeStyles(() => ({
  main: {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
  },
  representationArea: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
  },
}));

export const Workbench = ({
  editingContextId,
  initialRepresentationSelected,
  onRepresentationSelected,
  mainAreaComponent,
  readOnly,
  children,
}: WorkbenchProps) => {
  const classes = useWorkbenchStyles();
  const { registry } = useContext(RepresentationContext);
  const [{ value, context }, dispatch] = useMachine<WorkbenchContext, WorkbenchEvent>(workbenchMachine, {
    context: {
      displayedRepresentation: initialRepresentationSelected,
      representations: initialRepresentationSelected ? [initialRepresentationSelected] : [],
    },
  });
  const { toast } = value as SchemaValue;
  const { id, representations, displayedRepresentation, message } = context;

  const { error } = useSubscription<GQLEditingContextEventSubscription>(editingContextEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
      },
    },
    fetchPolicy: 'no-cache',
    onSubscriptionData: ({ subscriptionData }) => {
      const handleDataEvent: HandleSubscriptionResultEvent = {
        type: 'HANDLE_SUBSCRIPTION_RESULT',
        result: subscriptionData,
      };
      dispatch(handleDataEvent);
    },
    onSubscriptionComplete: () => {
      const completeEvent: HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
      dispatch(completeEvent);
    },
  });

  useEffect(() => {
    if (error) {
      const { message } = error;
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  }, [error, dispatch]);

  const [selection, setSelection] = useSelection();
  useEffect(() => {
    const updateSelectionEvent: UpdateSelectionEvent = {
      type: 'UPDATE_SELECTION',
      selection,
    };
    dispatch(updateSelectionEvent);
  }, [dispatch, selection]);

  const onRepresentationClick = (representation: Representation) => {
    setSelection({ entries: [{ id: representation.id, label: representation.label, kind: representation.kind }] });
  };

  const onClose = (representation: Representation) => {
    const hideRepresentationEvent: HideRepresentationEvent = { type: 'HIDE_REPRESENTATION', representation };
    dispatch(hideRepresentationEvent);
  };

  useEffect(() => {
    if (displayedRepresentation && displayedRepresentation.id !== initialRepresentationSelected?.id) {
      onRepresentationSelected(displayedRepresentation);
    } else if (displayedRepresentation === null && initialRepresentationSelected) {
      onRepresentationSelected(null);
    }
  }, [onRepresentationSelected, initialRepresentationSelected, displayedRepresentation]);

  const workbenchViewLeftSideContributions = [];
  const workbenchViewRightSideContributions = [];
  React.Children.forEach(children, (child) => {
    if (React.isValidElement(child) && child.type === WorkbenchViewContribution) {
      if (child.props.side === 'left') {
        workbenchViewLeftSideContributions.push(child);
      } else if (child.props.side === 'right') {
        workbenchViewRightSideContributions.push(child);
      }
    }
  });

  const MainComponent = mainAreaComponent;
  let main = (
    <MainComponent
      editingContextId={editingContextId}
      selection={selection}
      setSelection={setSelection}
      readOnly={readOnly}
    />
  );

  if (displayedRepresentation) {
    const RepresentationComponent = registry.getComponent(displayedRepresentation);
    const props: RepresentationComponentProps = {
      editingContextId,
      readOnly,
      representationId: displayedRepresentation.id,
      selection,
      setSelection,
    };
    main = (
      <div className={classes.representationArea} data-testid="representation-area">
        <RepresentationNavigation
          representations={representations}
          displayedRepresentation={displayedRepresentation}
          onRepresentationClick={onRepresentationClick}
          onClose={onClose}
        />
        <RepresentationComponent {...props} />
      </div>
    );
  }

  return (
    <>
      <Panels
        editingContextId={editingContextId}
        selection={selection}
        setSelection={setSelection}
        readOnly={readOnly}
        leftContributions={workbenchViewLeftSideContributions}
        leftPanelInitialSize={300}
        rightContributions={workbenchViewRightSideContributions}
        rightPanelInitialSize={300}
        mainArea={main}
      />
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
