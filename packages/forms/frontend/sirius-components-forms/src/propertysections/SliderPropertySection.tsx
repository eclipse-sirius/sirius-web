/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { theme, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
import Slider from '@mui/material/Slider';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLSlider } from '../form/FormEventFragments.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditSliderInput,
  GQLEditSliderMutationData,
  GQLEditSliderMutationVariables,
  GQLEditSliderPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
} from './SliderPropertySection.types';

export const editSliderMutation = gql`
  mutation editSlider($input: EditSliderInput!) {
    editSlider(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLEditSliderPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditSliderPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

const useSliderSectionStyles = makeStyles()((theme) => ({
  loadingIndicator: {
    color: theme.palette.divider,
    marginLeft: theme.spacing(2),
  },
  loadingBackdrop: {
    backgroundColor: 'transparent',
    boxShadow: 'none',
    zIndex: theme.zIndex.drawer + 1,
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

export const SliderPropertySection: PropertySectionComponent<GQLSlider> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLSlider>) => {
  const { classes } = useSliderSectionStyles();

  const [editSlider, { loading, data, error }] = useMutation<GQLEditSliderMutationData, GQLEditSliderMutationVariables>(
    editSliderMutation
  );

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  useEffect(() => {
    if (data) {
      const { editSlider } = data;
      if (isErrorPayload(editSlider) || isSuccessPayload(editSlider)) {
        addMessages(editSlider.messages);
      }
    }
  }, [data]);

  const onValueChanged = (_, value) => {
    const input: GQLEditSliderInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      sliderId: widget.id,
      newValue: value,
    };
    const variables: GQLEditSliderMutationVariables = { input };
    editSlider({ variables });
  };

  const [displayedValue, setDisplayedValue] = useState<number>(widget.currentValue);
  useEffect(() => {
    setDisplayedValue(widget.currentValue);
  }, [widget.currentValue]);

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          data-testid={widget.label}
        />
        {loading ? (
          <>
            <CircularProgress className={classes.loadingIndicator} size={`${theme.spacing(2)}`} />
            <Backdrop className={classes.loadingBackdrop} open={loading}></Backdrop>
          </>
        ) : null}
      </div>
      <Slider
        data-testid={widget.label}
        disabled={readOnly || widget.readOnly}
        min={widget.minValue}
        max={widget.maxValue}
        value={displayedValue}
        step={1}
        valueLabelDisplay="on"
        onChange={(_, value) => setDisplayedValue(value as number)}
        onChangeCommitted={onValueChanged}></Slider>
    </div>
  );
};
