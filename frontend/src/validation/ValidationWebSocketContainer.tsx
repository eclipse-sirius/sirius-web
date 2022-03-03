/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
import { useSubscription } from '@apollo/client';
import Accordion from '@material-ui/core/Accordion';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import makeStyles from '@material-ui/core/styles/makeStyles';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { useMachine } from '@xstate/react';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import { WorkbenchViewComponentProps } from 'workbench/Workbench.types';
import { GQLValidationEventSubscription } from './ValidationWebSocketContainer.types';
import {
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  ValidationWebSocketContainerContext,
  ValidationWebSocketContainerEvent,
  validationWebSocketContainerMachine,
} from './ValidationWebSocketContainerMachine';

const validationEventSubscription = gql`
  subscription validationEvent($input: ValidationEventInput!) {
    validationEvent(input: $input) {
      __typename
      ... on ValidationRefreshedEventPayload {
        id
        validation {
          id
          diagnostics {
            id
            kind
            message
          }
        }
      }
    }
  }
`;

const useValidationWebSocketContainerStyle = makeStyles((theme) => ({
  root: {
    padding: '8px',
  },
  heading: {
    flexBasis: '33.33%',
    flexShrink: 0,
  },
  secondaryHeading: {
    color: theme.palette.text.secondary,
  },
  accordionDetailsRoot: {
    flexDirection: 'column',
  },
  divider: {
    margin: '8px 0',
  },
  idle: {
    padding: theme.spacing(1),
  },
}));

export const ValidationWebSocketContainer = ({ editingContextId }: WorkbenchViewComponentProps) => {
  const classes = useValidationWebSocketContainerStyle();
  const [{ value, context }, dispatch] = useMachine<
    ValidationWebSocketContainerContext,
    ValidationWebSocketContainerEvent
  >(validationWebSocketContainerMachine);
  const { toast, validationWebSocketContainer } = value as SchemaValue;
  const { id, validation, message } = context;

  const { error } = useSubscription<GQLValidationEventSubscription>(validationEventSubscription, {
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

  let content = null;

  if (validationWebSocketContainer === 'ready') {
    const accordions = validation.categories.map((category) => {
      const details = category.diagnostics
        .map<React.ReactNode>((diagnostic) => {
          return <Typography key={diagnostic.id}>{diagnostic.message}</Typography>;
        })
        .reduce((prev, current, index) => [
          prev,
          <Divider key={`Divider-${index}`} className={classes.divider} />,
          current,
        ]);

      return (
        <Accordion key={category.kind}>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography className={classes.heading}>{category.kind}</Typography>
            <Typography className={classes.secondaryHeading}>{category.diagnostics.length} diagnostics</Typography>
          </AccordionSummary>
          <AccordionDetails className={classes.accordionDetailsRoot}>{details}</AccordionDetails>
        </Accordion>
      );
    });

    if (accordions.length > 0) {
      content = <div className={classes.root}>{accordions}</div>;
    } else {
      content = (
        <div className={classes.idle}>
          <Typography variant="subtitle2">No diagnostic available</Typography>
        </div>
      );
    }
  }

  return (
    <>
      {content}
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
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
