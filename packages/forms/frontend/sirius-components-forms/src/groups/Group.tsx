/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { useMutation } from '@apollo/client';
import { ServerContext } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import ToggleButton from '@material-ui/lab/ToggleButton';
import ToggleButtonGroup from '@material-ui/lab/ToggleButtonGroup';
import gql from 'graphql-tag';
import { useContext, useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { GQLWidget } from '../form/FormEventFragments.types';
import {
  GQLPushButtonInput,
  GQLPushButtonMutationData,
  GQLPushButtonMutationVariables,
} from '../propertysections/ButtonPropertySection.types';
import { PropertySection } from '../propertysections/PropertySection';
import { GroupProps } from './Group.types';

const useGroupStyles = makeStyles((theme) => ({
  group: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(1),
  },
  title: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  verticalSections: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
  adaptableSections: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: theme.spacing(2),
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
  button: {
    paddingTop: '1px',
    paddingBottom: '0px',
  },
  buttonIcon: {
    marginRight: theme.spacing(1),
  },
}));

export const pushButtonMutation = gql`
  mutation pushButton($input: PushButtonInput!) {
    pushButton(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const Group = ({ editingContextId, formId, group, widgetSubscriptions, setSelection, readOnly }: GroupProps) => {
  const classes = useGroupStyles();
  const [visibleWidgetIds, setVisibleWidgetIds] = useState<string[]>([]);
  const { httpOrigin } = useContext(ServerContext);

  useEffect(() => {
    setVisibleWidgetIds(group.widgets.map((widget) => widget.id));
  }, [group]);

  let widgetSelector = undefined;
  if (group.displayMode === 'TOGGLEABLE_AREAS') {
    widgetSelector = (
      <ToggleButtonGroup value={visibleWidgetIds} onChange={(_, newVisibleIds) => setVisibleWidgetIds(newVisibleIds)}>
        {group.widgets.map((widget) => {
          return (
            <ToggleButton className={classes.button} value={widget.id} key={widget.id}>
              {widget.iconURL && (
                <img
                  className={classes.buttonIcon}
                  height="16"
                  width="16"
                  alt={widget.label}
                  src={httpOrigin + widget.iconURL}
                />
              )}
              {widget.label}
            </ToggleButton>
          );
        })}
      </ToggleButtonGroup>
    );
  }

  const [pushButton] = useMutation<GQLPushButtonMutationData, GQLPushButtonMutationVariables>(pushButtonMutation);

  const onClick = (button: GQLWidget) => {
    const input: GQLPushButtonInput = {
      id: uuid(),
      editingContextId,
      representationId: formId,
      buttonId: button.id,
    };
    const variables: GQLPushButtonMutationVariables = { input };
    pushButton({ variables });
  };

  let toolbar = null;
  if (group.buttons?.length > 0) {
    toolbar = (
      <ButtonGroup size="small" variant="contained">
        {group.buttons.map((button) => (
          <Button key={button.id} disabled={readOnly} onClick={() => onClick(button)}>
            {button.label}
          </Button>
        ))}
      </ButtonGroup>
    );
  }

  return (
    <div className={classes.group}>
      {group.displayMode === 'TOGGLEABLE_AREAS' ? (
        widgetSelector
      ) : (
        <Typography variant="subtitle1" className={classes.title} gutterBottom>
          {group.label}
        </Typography>
      )}
      {toolbar}
      <div className={group.displayMode === 'LIST' ? classes.verticalSections : classes.adaptableSections}>
        {group.widgets
          .filter((widget) => visibleWidgetIds.includes(widget.id))
          .map((widget) => (
            <PropertySection
              editingContextId={editingContextId}
              formId={formId}
              widget={widget}
              widgetSubscriptions={widgetSubscriptions}
              setSelection={setSelection}
              readOnly={readOnly}
              key={widget.id}
            />
          ))}
      </div>
    </div>
  );
};
