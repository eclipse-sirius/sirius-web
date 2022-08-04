/*******************************************************************************
 * Copyright (c) 2022 Obeo and others.
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
import { WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Divider,
  IconButton,
  Snackbar,
  Typography,
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Close as CloseIcon, ExpandMore as ExpandMoreIcon } from '@material-ui/icons';
import { useMachine } from '@xstate/react';
import React, { useEffect } from 'react';
import { GQLValidationEventSubscription } from './ValidationView.types';
import {
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  ValidationViewContext,
  ValidationViewEvent,
  validationViewMachine,
} from './ValidationViewMachine';

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

const useValidationViewStyle = makeStyles((theme) => ({
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

export const ValidationView = ({ editingContextId }: WorkbenchViewComponentProps) => {
  const classes = useValidationViewStyle();
  const [{ value, context }, dispatch] = useMachine<ValidationViewContext, ValidationViewEvent>(validationViewMachine);
  const { toast, validationView } = value as SchemaValue;
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

  if (validationView === 'ready') {
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
